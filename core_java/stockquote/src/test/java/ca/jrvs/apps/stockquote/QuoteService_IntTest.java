package ca.jrvs.apps.stockquote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuoteService_IntTest {
  static DatabaseConnectionManager dcm;
  static Connection connection;
  QuoteService quoteService;
  QuoteDao quoteDao;

  @BeforeEach
  void init() throws SQLException {
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "5432", "postgres", "password");
    connection = dcm.getConnection();
    quoteDao = new QuoteDao(connection);
    quoteService = new QuoteService(quoteDao, new QuoteHttpHelper("api-key", new OkHttpClient()));

    Quote quote = new Quote( "IBM",186.6300,188.9500,185.9452,
        187.6400,4842840, Date.valueOf("2024-02-16"),186.8700,
        0.6500,"0.4121%", Timestamp.from(Instant.now()));
    quoteDao.save(quote);

  }

  @Test
  void test_fetch_valid_new() {
    Optional<Quote> oldQuote = quoteDao.findById("MSFT");
    assertFalse(oldQuote.isPresent());

    quoteService.fetchQuoteDataFromAPI("MSFT");
    Optional<Quote> newQuote = quoteDao.findById("MSFT");
    assertEquals(newQuote.get().getTicker(), "MSFT");
  }

  @Test
  void test_fetch_invalid() {
    assertThrows(IllegalArgumentException.class, () -> quoteService.fetchQuoteDataFromAPI("fake"));
  }

  @Test
  void test_fetch_valid_existing() {
    Optional<Quote> oldQuote = quoteDao.findById("IBM");
    Optional<Quote> actual = quoteService.fetchQuoteDataFromAPI("IBM");
    // There should be an update to the DB since it's existing
    assertNotEquals(actual.get().getTimestamp(), oldQuote.get().getTimestamp());
  }
}
