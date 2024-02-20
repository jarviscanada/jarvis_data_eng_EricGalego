package ca.jrvs.apps.stockquote;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PositionDao_Test {

  static PositionDao positionDao;
  static DatabaseConnectionManager dcm;
  static Connection connection;

  @BeforeAll
  static void setup() throws SQLException {
    dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");
    connection = dcm.getConnection();
    QuoteDao quoteDao = new QuoteDao(connection);
    positionDao = new PositionDao(connection);

    // Setup because position has fk with quotes
    Quote quote = new Quote( "IBM",186.6300,188.9500,185.9452,
        187.6400,4842840, Date.valueOf("2024-02-16"),186.8700,
        0.6500,"0.4121%", Timestamp.from(Instant.now()));
    quoteDao.save(quote);
    Quote quote2 = new Quote( "MSFT",186.6300,188.9500,185.9452,
        187.6400,4842840, Date.valueOf("2024-02-16"),186.8700,
        0.6500,"0.4121%", Timestamp.from(Instant.now()));
    quoteDao.save(quote2);
  }

  @BeforeEach
  void init() {
    // create default IBM reset it every time for each test
    Position position = new Position();
    position.setTicker("IBM");
    position.setValuePaid(500.15);
    position.setNumOfShares(20);

    positionDao.save(position);
  }

  @Test
  void test_update() {
    Position position = new Position();
    position.setTicker("IBM");
    position.setValuePaid(595.15);
    position.setNumOfShares(25);

    Position actual = positionDao.save(position);
    assertEquals("IBM", actual.getTicker());
    assertEquals(25, actual.getNumOfShares());
    assertEquals(595.15, actual.getValuePaid());
  }

  @Test
  void test_create() {
    Position position = new Position();
    position.setTicker("MSFT");
    position.setValuePaid(585.15);
    position.setNumOfShares(28);

    Position actual = positionDao.save(position);
    assertEquals("MSFT", actual.getTicker());
    assertEquals(28, actual.getNumOfShares());
    assertEquals(585.15, actual.getValuePaid());
  }

  @Test
  void test_find_by_valid() {
    Optional<Position> actual = positionDao.findById("IBM");

    assertEquals(actual.get().getTicker(), "IBM");
    assertEquals(actual.get().getNumOfShares(), 20);
  }

  @Test
  void test_find_by_dne() {
    Optional<Position> actual = positionDao.findById("fakeCompany");

    assertEquals(actual, Optional.empty());
  }

  @Test
  void test_find_all() {
    Position positionMSFT = new Position();
    positionMSFT.setTicker("MSFT");
    positionMSFT.setValuePaid(585.15);
    positionMSFT.setNumOfShares(28);

    positionDao.save(positionMSFT);
    List<Position> positions = positionDao.findAll();

    long numIBM = positions.stream().filter(position -> position.getTicker().equals("IBM")).count();
    long numMSFT = positions.stream().filter(position -> position.getTicker().equals("MSFT")).count();
    assertEquals(positions.size(), 2);
    assertEquals(numIBM, 1);
    assertEquals(numMSFT, 1);
  }

  @Test
  void test_delete_by_id_valid() {
    positionDao.deleteById("IBM");
    Optional<Position> actual = positionDao.findById("IBM");

    assertEquals(actual, Optional.empty());
  }

  @Test
  void test_delete_by_id_dne() {
    positionDao.deleteById("fake");
    Optional<Position> actual = positionDao.findById("fake");

    assertEquals(actual, Optional.empty());
  }

  @Test
  void test_delete_all() {
    Position position = new Position();
    position.setTicker("MSFT");
    position.setValuePaid(585.15);
    position.setNumOfShares(28);
    positionDao.save(position);

    positionDao.deleteAll();
    List<Position> actual = positionDao.findAll();

    assert(actual.isEmpty());
  }
}
