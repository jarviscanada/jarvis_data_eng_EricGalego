package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

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
        userResponse = br.readLine();
        Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(userResponse);
        System.out.println(quote.get());

        Quote quoteResp = quote.get();

        System.out.println(
            "Would you like to buy or sell this stock? (Input buy or sell or press any key to check another stock): ");
        userResponse = br.readLine();

        if (!userResponse.equals("buy") && !userResponse.equals("sell")) {
          System.out.println("Type in a stock to buy or sell: ");
        }
        if (userResponse.equals("buy")) {
          System.out.println("Please enter how many shares to purchase: ");

          try {
            int numberShares = Integer.parseInt(br.readLine());
            positionService.buy(quoteResp.getTicker(), numberShares,
                quoteResp.getPrice() * numberShares);
          } catch (NumberFormatException e) {
            System.out.println("Please input a valid number of shares.");
            break;
          }
        } else {
          positionService.sell(quoteResp.getTicker());
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
