package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  public final static Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Map<String, String> properties = parseProperties();
    OkHttpClient client = new OkHttpClient();
    try (Connection c = new DatabaseConnectionManager(properties.get("server"), properties.get("database"),
        properties.get("port"), properties.get("username"), properties.get("password")).getConnection()) {
      logger.info("Initializing services.");
      QuoteDao qRepo = new QuoteDao(c);
      PositionDao pRepo = new PositionDao(c);
      QuoteHttpHelper rcon = new QuoteHttpHelper(properties.get("api-key"), client);
      QuoteService sQuote = new QuoteService(qRepo, rcon);
      PositionService sPos = new PositionService(pRepo, sQuote);
      StockQuoteController con = new StockQuoteController(sQuote, sPos);
      con.initClient();
    } catch (SQLException e) {
      logger.error(e.getMessage());
    }
  }

  public static Map<String, String> parseProperties() {
    Map<String, String> properties = new HashMap<>();
    Scanner sc;
    try {
      logger.info("Parsing properties file.");
      sc = new Scanner(new File("src/main/resources/properties.txt"));
    } catch (FileNotFoundException e) {
      logger.error("src/main/resources/properties.txt file does not exist.");
      throw new RuntimeException(e);
    }

    while (sc.hasNextLine()) {
      String currLine = sc.nextLine();
      String[] currProp = currLine.split(":");
      properties.put(currProp[0], currProp[1]);
    }
    return properties;
  }
}
