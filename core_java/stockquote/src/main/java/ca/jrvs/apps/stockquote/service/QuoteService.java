package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.util.Optional;

import static ca.jrvs.apps.stockquote.Main.logger;

public class QuoteService {
  private QuoteDao dao;
  private QuoteHttpHelper httpHelper;

  public QuoteService(QuoteDao dao, QuoteHttpHelper httpHelper) {
    this.dao = dao;
    this.httpHelper = httpHelper;
  }

  /**
   * Fetches latest quote data from endpoint
   * @param ticker
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
    try {
      Quote quote = httpHelper.fetchQuoteInfo(ticker);
      return Optional.ofNullable(dao.save(quote));
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
      return Optional.empty();
    }
  }
}
