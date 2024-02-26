package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.util.Optional;

import static ca.jrvs.apps.stockquote.Main.logger;

public class PositionService {
  private PositionDao positionDao;
  private QuoteService quoteService;

  public PositionService(PositionDao positionDao, QuoteService quoteService) {
    this.positionDao = positionDao;
    this.quoteService = quoteService;
  }

  /**
   * Processes a buy order and updates the database accordingly.
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) throws IllegalArgumentException {

    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker);
    if(!quote.isPresent()) {
      logger.error("Invalid ticker: {}", ticker);
      throw new IllegalArgumentException("Invalid ticker.");
    }

    int volume = quote.get().getVolume();
    if(numberOfShares > volume) {
      logger.error("Not enough shares of: {}", ticker);
      throw new IllegalArgumentException("Not enough shares to purchase.");
    }
    Optional<Position> position = positionDao.findById(ticker);

    if(position.isPresent()) {
      position.get().setNumOfShares(position.get().getNumOfShares()+numberOfShares);
      position.get().setValuePaid(position.get().getValuePaid()+price*numberOfShares);
      return positionDao.save(position.get());
    } else {
      Position newPosition = new Position();
      newPosition.setTicker(ticker);
      newPosition.setNumOfShares(numberOfShares);
      newPosition.setValuePaid(price*numberOfShares);
      return positionDao.save(newPosition);
    }
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {

    Optional<Position> position = positionDao.findById(ticker);
    if (position.isPresent()) {
      positionDao.deleteById(ticker);
      return;
    }
    System.out.println("You do not own any of these stocks.");
  }

  /**
   * Find the position if it exists and prints to the user
   * @param ticker
   */
  public Optional<Position> findbyId(String ticker) {
    return positionDao.findById(ticker);
  }
}
