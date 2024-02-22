package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class QuoteService {
  private QuoteDao dao;
  private QuoteHttpHelper httpHelper;
  private DatabaseConnectionManager dcm;
  private Connection connection;
  //use verify instead of assert

  /**
   * Fetches latest quote data from endpoint
   * @param ticker
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
    try {
      dcm = new DatabaseConnectionManager("localhost",
          "stock_quote", "postgres", "password");
      connection = dcm.getConnection();
      httpHelper = new QuoteHttpHelper();
      dao = new QuoteDao(connection);
    } catch(SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
    Optional<Quote> quote = Optional.ofNullable(httpHelper.fetchQuoteInfo(ticker));
    return Optional.ofNullable(dao.save(quote.get()));
  }
}
