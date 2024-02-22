package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import okhttp3.OkHttpClient;

public class Main {

  static StockQuoteController stockQuoteController;

  public static void main(String[] args) {
    //load properties file
    Map<String, String> properties = new HashMap<>();
    Scanner sc = new Scanner("src/main/resources/properties.txt");

    while (sc.hasNextLine()) {
      String currLine = sc.nextLine();
      String[] currProp = currLine.split(":");
      properties.put(currProp[0], currProp[1]);
    }

    OkHttpClient client = new OkHttpClient();
    try (Connection c = new DatabaseConnectionManager(properties.get("server"), properties.get("database"),
        properties.get("port"), properties.get("username"), properties.get("password")).getConnection()) {
      QuoteDao qRepo = new QuoteDao(c);
      PositionDao pRepo = new PositionDao(c);
      QuoteHttpHelper rcon = new QuoteHttpHelper(properties.get("api-key"), client);
      QuoteService sQuote = new QuoteService(qRepo, rcon);
      PositionService sPos = new PositionService(pRepo, qRepo);
      StockQuoteController con = new StockQuoteController(sQuote, sPos);
      con.initClient();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
