package ca.jrvs.apps.stockquote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuoteHttpHelper {

  private String apiKey;
  private OkHttpClient client;
  public String respStr = "{\n"
      + "    \"Global Quote\": {\n"
      + "        \"01. symbol\": \"MSFT\",\n"
      + "        \"02. open\": \"404.8400\",\n"
      + "        \"03. high\": \"405.0710\",\n"
      + "        \"04. low\": \"403.4000\",\n"
      + "        \"05. price\": \"406.3200\",\n"
      + "        \"06. volume\": \"27774589\",\n"
      + "        \"07. latest trading day\": \"2024-02-13\",\n"
      + "        \"08. previous close\": \"415.2600\",\n"
      + "        \"09. change\": \"-8.9400\",\n"
      + "        \"10. change percent\": \"-2.1529%\"\n"
      + "    }\n"
      + "}\n";


  /**
   * Fetch latest quote data from Alpha Vantage endpoint
   * @param symbol
   * @return Quote with the latest data
   * @throws IllegalArgumentException - if no data was found for the given symbol
   */
  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
    client = new OkHttpClient();

    String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json&apikey=a";
    Request request = new Request.Builder()
        .url(url)
        .build();
    try {
      Response response = client.newCall(request).execute();
      String respStr = response.body().string();
      System.out.println(respStr);

      ObjectMapper objectMapper = new ObjectMapper();
      try {
        JsonNode rootNode = objectMapper.readTree(respStr);
        JsonNode dataNode = rootNode.get("Global Quote");
        if (dataNode.size() == 0) {
          throw new IllegalArgumentException("No data was found with this symbol.");
        }
        Quote quote = objectMapper.readValue(dataNode.toString(), Quote.class);
        quote.setTimestamp(new Timestamp(new Date().getTime()));
        return quote;
        // Global Quote DNE
      } catch(NullPointerException e) {
        throw new IllegalArgumentException("No data was found with this symbol.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
