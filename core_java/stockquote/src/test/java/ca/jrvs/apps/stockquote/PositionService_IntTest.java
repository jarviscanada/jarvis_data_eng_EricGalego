package ca.jrvs.apps.stockquote;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.service.PositionService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositionService_IntTest {

  static PositionDao positionDao;
  static DatabaseConnectionManager dcm;
  static Connection connection;
  static PositionService positionService;

  @BeforeAll
  static void setup() throws SQLException {
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "5432", "postgres", "password");
    connection = dcm.getConnection();
    QuoteDao quoteDao = new QuoteDao(connection);
    positionDao = new PositionDao(connection);
    positionService = new PositionService(positionDao, quoteDao);

    Quote quote = new Quote( "IBM",186.6300,188.9500,185.9452,
        187.6400,4842840, Date.valueOf("2024-02-16"),186.8700,
        0.6500,"0.4121%", Timestamp.from(Instant.now()));
    quoteDao.save(quote);
  }

  @BeforeEach
  void init() {
    // create default IBM reset it every time for each test
    Position position = new Position();
    position.setTicker("IBM");
    position.setValuePaid(500);
    position.setNumOfShares(1);
    positionDao.save(position);
  }

  @Test
  void test_buy_valid_new() {
    Position position = positionService.buy("MSFT", 20, 30.4);
    assertEquals(position.getTicker(), "MSFT");
    assertEquals(position.getValuePaid(), 30.4);
    assertEquals(position.getNumOfShares(), 20);
  }

  @Test
  void test_buy_valid_existing() {
    Position position = positionService.buy("IBM", 20, 30.5);
    assertEquals(position.getTicker(), "IBM");
    assertEquals(position.getValuePaid(), 530.5);
    assertEquals(position.getNumOfShares(), 21);
  }

  @Test
  void test_buy_invalid() {
    assertThrows(IllegalArgumentException.class, () -> positionService.buy("fake", 99, 12.3));
  }

  @Test
  void test_buy_not_enough_shares() {
    assertThrows(RuntimeException.class, () -> positionService.buy("IBM", 99999999,  20.50));
  }

  @Test
  void test_sell_valid() {
    positionService.sell("IBM");
    Optional<Position> position = positionDao.findById("IBM");
    assertFalse(position.isPresent());
  }

  //TODO could have this throw an error but like it just won't find anything with that id
  @Test
  void test_sell_dne() {
    positionService.sell("fake");
  }
}
