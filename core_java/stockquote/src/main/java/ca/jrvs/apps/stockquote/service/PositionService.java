package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import java.util.Optional;

public class PositionService {
  private PositionDao positionDao;
  private QuoteDao quoteDao;

  public PositionService(PositionDao positionDao, QuoteDao quoteDao) {
    this.positionDao = positionDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) {

    Optional<Quote> quote = quoteDao.findById(ticker);

    int volume = quote.get().getVolume();
    if(numberOfShares > volume) {
      throw new RuntimeException("Not enough shares to purchase.");
    }
    Optional<Position> position = positionDao.findById(ticker);

    if(position.isPresent()) {
      position.get().setNumOfShares(position.get().getNumOfShares()+numberOfShares);
      position.get().setValuePaid(position.get().getValuePaid()+price);
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

    Optional<Quote> quote = quoteDao.findById(ticker);
    //TODO could change this it's unnecessary unless I end up throwing an error if not present
    if (quote.isPresent()) {
      positionDao.deleteById(ticker);
    }
  }
}
