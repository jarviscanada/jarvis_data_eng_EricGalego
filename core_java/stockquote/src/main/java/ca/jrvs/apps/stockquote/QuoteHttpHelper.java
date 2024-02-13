package ca.jrvs.apps.stockquote;

import static ca.jrvs.apps.stockquote.JsonParser.toObjectFromJson;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class QuoteHttpHelper {

  private String apiKey;
  private OkHttpClient client;


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

      JSONObject jsonResp = new JSONObject(respStr);
      // if symbol or api key was bad which gives empty response or error message
      if (jsonResp.has("Error Message") || jsonResp.getJSONObject("Global Quote").length() == 0) {
        throw new IllegalArgumentException("No data was found with this symbol.");
      }
      return toObjectFromJson(jsonResp.getJSONObject("Global Quote").toString(), Quote.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    QuoteHttpHelper helper = new QuoteHttpHelper();
    Quote quote = helper.fetchQuoteInfo("MSFT");
    System.out.println(quote);
  }
}
