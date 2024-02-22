package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.util.Optional;

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
    Optional<Quote> quote = Optional.ofNullable(httpHelper.fetchQuoteInfo(ticker));
    return Optional.ofNullable(dao.save(quote.get()));
  }
}
