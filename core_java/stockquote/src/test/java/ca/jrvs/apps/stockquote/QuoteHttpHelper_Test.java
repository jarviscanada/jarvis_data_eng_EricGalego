package ca.jrvs.apps.stockquote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuoteHttpHelper_Test {

    QuoteHttpHelper quoteHttpHelper;

    public String respStr = "{\n"
        + "    \"Global Quote\": {\n"
        + "        \"01. symbol\": \"MSFT\",\n"
        + "        \"02. open\": \"404.8400\",\n"
        + "        \"03. high\": \"410.0710\",\n"
        + "        \"04. low\": \"403.4000\",\n"
        + "        \"05. price\": \"406.3200\",\n"
        + "        \"06. volume\": \"27774589\",\n"
        + "        \"07. latest trading day\": \"2024-02-13\",\n"
        + "        \"08. previous close\": \"415.2600\",\n"
        + "        \"09. change\": \"-8.9400\",\n"
        + "        \"10. change percent\": \"-2.1529%\"\n"
        + "    }\n"
        + "}\n";

  String emptyResponse = "{\n"
      + "    \"Global Quote\": {}\n"
      + "}";

  String errorResp = "{\n"
      + "    \"Error Message\": \"the parameter apikey is invalid or missing. Please claim your free API key on (https://www.alphavantage.co/support/#api-key). It should take less than 20 seconds.\"\n"
      + "}";

    @BeforeEach
    void init() {
      quoteHttpHelper = new QuoteHttpHelper("api-key", new OkHttpClient());
    }

    @Test
    void test_good_symbol() {
      Quote actual = quoteHttpHelper.fetchQuoteInfo("MSFT");

      assertEquals("MSFT", actual.getTicker());
    }

    @Test
    void test_bad_symbol() {
      assertThrows(IllegalArgumentException.class, () -> quoteHttpHelper.fetchQuoteInfo("fake"));
    }

    @Test
    void test_bad_url() {
      assertThrows(IllegalArgumentException.class, () -> quoteHttpHelper.fetchQuoteInfo("function=FAKE&apikey"));
    }
}
