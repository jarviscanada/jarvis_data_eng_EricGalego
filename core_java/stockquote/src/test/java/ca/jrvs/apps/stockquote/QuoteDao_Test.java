package ca.jrvs.apps.stockquote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuoteDao_Test {

  QuoteDao quoteDao;
  DatabaseConnectionManager dcm;
  Connection connection;

  @Mock
  QuoteHttpHelper quoteHttpHelper;

  @BeforeEach
  void init() throws SQLException {
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");
    connection = dcm.getConnection();
    quoteDao = new QuoteDao(connection);
    quoteHttpHelper = new QuoteHttpHelper();

    Quote quote = new Quote( "IBM",186.6300,188.9500,185.9452,
        187.6400,4842840, Date.valueOf("2024-02-16"),186.8700,
        0.6500,"0.4121%", Timestamp.from(Instant.now()));
    quoteDao.save(quote);
  }

  @Test
  void test_update() {
    Quote quote = new Quote( "IBM",186.6300,188.9500,185.9452,
        187.6400,4842840, Date.valueOf("2024-02-16"),186.8700,
        0.7700,"0.4121%", Timestamp.from(Instant.now()));

    Quote actual = quoteDao.save(quote);
    assertEquals("IBM", actual.getTicker());
    assertEquals(0.7700, actual.getChange());
  }

  @Test
  void test_create() {
    Quote quote = new Quote("MSFT", 404.8400, 410.0710, 403.4000,
        406.3200, 27774589, Date.valueOf("2024-02-13"),415.2600,
        -8.9400, "-2.1529%", Timestamp.from(Instant.now()));

    Quote actual = quoteDao.save(quote);
    assertEquals("MSFT", actual.getTicker());
    assertEquals(-8.9400, actual.getChange());
  }

  @Test
  void test_find_by_valid() {
    Optional<Quote> actual = quoteDao.findById("IBM");

    assertEquals(actual.get().getTicker(), "IBM");
    assertEquals(actual.get().getChange(), 0.6500);
  }

  @Test
  void test_find_by_dne() {
    Optional<Quote> actual = quoteDao.findById("fakeCompany");

    assertEquals(actual, Optional.empty());
  }

  @Test
  void test_find_all() {
    Quote quoteMSFT = new Quote("MSFT", 404.8400, 410.0710, 403.4000,
        406.3200, 27774589, Date.valueOf("2024-02-13"),415.2600,
        -8.9400, "-2.1529%", Timestamp.from(Instant.now()));

    quoteDao.save(quoteMSFT);
    List<Quote> quotes = quoteDao.findAll();

    long numIBM = quotes.stream().filter(quote -> quote.getTicker().equals("IBM")).count();
    long numMSFT = quotes.stream().filter(quote -> quote.getTicker().equals("MSFT")).count();
    assertEquals(quotes.size(), 2);
    assertEquals(numIBM, 1);
    assertEquals(numMSFT, 1);
  }

  @Test
  void test_delete_by_id_valid() {
    quoteDao.deleteById("IBM");
    Optional<Quote> actual = quoteDao.findById("IBM");

    assertEquals(actual, Optional.empty());
  }

  @Test
  void test_delete_by_id_dne() {
    quoteDao.deleteById("fake");
    Optional<Quote> actual = quoteDao.findById("fake");

    assertEquals(actual, Optional.empty());
  }

  @Test
  void test_delete_all() {
    Quote quoteMSFT = new Quote("MSFT", 404.8400, 410.0710, 403.4000,
        406.3200, 27774589, Date.valueOf("2024-02-13"),415.2600,
        -8.9400, "-2.1529%", Timestamp.from(Instant.now()));

    quoteDao.save(quoteMSFT);

    quoteDao.deleteAll();
    List<Quote> actual = quoteDao.findAll();

    assert(actual.isEmpty());
  }


  public String respStrMSFT = "{\n"
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

  public String respStrIBM = "{\n"
      + "    \"Global Quote\": {\n"
      + "        \"01. symbol\": \"IBM\",\n"
      + "        \"02. open\": \"186.6300\",\n"
      + "        \"03. high\": \"188.9500\",\n"
      + "        \"04. low\": \"185.9452\",\n"
      + "        \"05. price\": \"187.6400\",\n"
      + "        \"06. volume\": \"4842840\",\n"
      + "        \"07. latest trading day\": \"2024-02-16\",\n"
      + "        \"08. previous close\": \"186.8700\",\n"
      + "        \"09. change\": \"0.7700\",\n"
      + "        \"10. change percent\": \"0.4121%\"\n"
      + "    }\n"
      + "}";

  String emptyResponse = "{\n"
      + "    \"Global Quote\": {}\n"
      + "}";

  String errorResp = "{\n"
      + "    \"Error Message\": \"the parameter apikey is invalid or missing. Please claim your free API key on (https://www.alphavantage.co/support/#api-key). It should take less than 20 seconds.\"\n"
      + "}";
}
