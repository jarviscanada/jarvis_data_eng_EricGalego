package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static ca.jrvs.apps.stockquote.Main.logger;

public class StockQuoteController {

  private QuoteService quoteService;
  private PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  /**
   * User interface for our application
   */
  public void initClient() {

    while(true) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String userResponse;

      System.out.println("Type in a stock to buy or sell: ");
      try {
        String stock = br.readLine();
        try {
          Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(stock);
          Optional<Position> position = positionService.findbyId(stock);

          System.out.println("Latest Stock Information: ");
          System.out.println(quote.get());
          System.out.println("Your Position with this stock: ");
          if(position.isPresent()) {
            System.out.println(position.get());
          } else {
            System.out.println("You don't own any of this stock.");
          }

          Quote quoteResp = quote.get();
          System.out.println(
                  "Would you like to buy or sell this stock? (Input buy or sell or press any key to check another stock): ");
          userResponse = br.readLine();

          if (!userResponse.equals("buy") && !userResponse.equals("sell")) {
            continue;
          }
          if (userResponse.equals("buy")) {
            System.out.println("Please enter how many shares to purchase: ");

            try {
              int numberShares = Integer.parseInt(br.readLine());
              positionService.buy(quoteResp.getTicker(), numberShares, quoteResp.getPrice());
              System.out.printf("You bought: %s %s shares for %s \n", numberShares, quoteResp.getTicker(), quoteResp.getPrice()*numberShares);
            } catch (NumberFormatException e) {
              System.out.println("Please input a valid number of shares.");
            } catch (IllegalArgumentException e) {
              System.out.println(e.getMessage());
            }
          } else {
            positionService.sell(stock);
            if(position.isPresent()) {
              System.out.printf("You sold: %s %s shares for %s \n", position.get().getNumOfShares(), position.get().getTicker(), position.get().getValuePaid());
              continue;
            }
            System.out.println("You didn't have any of this stock to sell.");
          }
        } catch(IllegalArgumentException e) {
          System.out.println("Please type in a valid stock.");
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw new RuntimeException(e);
      }
    }
  }
}
