#!/bin/bash

# Start PostgreSQL
docker-entrypoint.sh postgres &

# Wait for PostgreSQL to be ready
until pg_isready -U $POSTGRES_USER; do
  echo "Waiting for PostgreSQL to start..."
  sleep 1
done

# Once PostgreSQL is ready, start the Java application
java -jar /usr/local/app/stockquote/stockquote.jar