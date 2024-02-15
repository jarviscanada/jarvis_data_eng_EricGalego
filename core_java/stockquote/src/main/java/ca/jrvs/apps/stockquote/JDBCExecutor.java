package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
        "stock_quote", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
      Quote quote = quoteHttpHelper.fetchQuoteInfo("IBM");

      QuoteDao quoteDao = new QuoteDao(connection);
      Quote temp = quoteDao.save(quote);
      System.out.println(temp);
      Optional<Quote> temp2 = quoteDao.findById("1");
      System.out.println(temp2);
      Optional<Quote> temp3 = quoteDao.findById("IBM");
      System.out.println(temp3);

      quoteDao.deleteById("1");

      quoteDao.findAll().forEach(System.out::println);

    } catch (SQLException e){
      e.printStackTrace();
    }
  }
}
