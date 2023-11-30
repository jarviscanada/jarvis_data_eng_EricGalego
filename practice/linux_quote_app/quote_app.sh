#!/bin/bash

api_key=$1
psql_host=$2
psql_port=$3
db_name=$4
psql_user=$5
psql_pass=$6

symbols=${@: 7}

if [ $# -lt 7 ]; then
  echo 'You do not have the correct number of arguments'
  exit 1
fi

for symbol in $symbols
do
  response=$(curl --request GET \
    --url "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=${symbol}&datatype=json" \
    --header 'X-RapidAPI-Host: alpha-vantage.p.rapidapi.com' \
    --header "X-RapidAPI-Key: ${api_key}")

  valid_resp=$( jq 'has("Global Quote")' <<< "$response")
  
  if ! $valid_resp; then
    echo 'API request failed. Try again.'
    exit 1
  fi
  
  symbol=$(jq '."Global Quote"."01. symbol"' <<< "$response")
  open=$(jq '."Global Quote"."02. open"' <<< "$response")
  high=$(jq '."Global Quote"."03. high"' <<< "$response")
  low=$(jq '."Global Quote"."04. low"' <<< "$response")
  price=$(jq '."Global Quote"."05. price"' <<< "$response")
  volume="$(jq '."Global Quote"."06. volume"' <<< "$response")"
  
  # Convert to INT before inserting
  volume=$(echo "$volume" | bc)
  open=$(echo "$open" | bc)
  high=$(echo "$high" | bc)
  low=$(echo "$low" | bc)
  price=$(echo "$price" | bc)

  # If empty we do not append (-z was not working)
  if [[ "$symbol" = "null" ]]; then
    echo 'No results try different symbol'
  else
    insert_stmt="INSERT INTO quotes(symbol, open, high, low, price, volume)
    VALUES('$symbol', '$open', '$high', '$low', '$price', '$volume')"

    export PGPASSWORD=$psql_pass
    psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
  fi
done

exit $?