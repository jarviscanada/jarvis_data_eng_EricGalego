package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.IexQuote;
import ca.jrvs.apps.trading.MarketDataConfig;
import ca.jrvs.apps.trading.utils.JsonParser;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private final String URL;

  private final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  HttpClientConnectionManager httpClientConnectionManager;
  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    URL = marketDataConfig.getHost() + "stock/market/batch?symbols=%s&types=quote&token=" + marketDataConfig.getToken();
  }

  /**
   * Get a single IexQuote
   *
   * @param ticker
   * @throws IllegalArgumentException      if a given ticker is invalid
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    }
    return Optional.of(quotes.get(0));
  }

  /**
   * Get quotes from IEX
   *
   * @param tickers is a ist of tickers
   * @throws IllegalArgumentException      if ticker is invalid or tickers is emtpy
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    if (Iterables.size(tickers) == 0) {
      throw new IllegalArgumentException("Tickers is empty");
    }
    String url = String.format(URL, String.join(",", tickers));
    Optional<String> response = executeHttpGet(url);
    JSONObject jsonResp;
    try {
      jsonResp = new JSONObject(response.get());
    } catch(JSONException e) {
      throw new IllegalArgumentException("Symbol does not exist");
    }
    List<IexQuote> quotes = new ArrayList<>();
    tickers.forEach(ticker -> {
      if (!jsonResp.has(ticker.toUpperCase())) {
        throw new IllegalArgumentException("ERROR: Ticker " + ticker + " is invalid");
      }
      try {
        quotes.add(JsonParser
            .toObjectFromJson(
                jsonResp.getJSONObject(ticker.toUpperCase()).getJSONObject("quote").toString(),
                IexQuote.class));
      } catch (IOException e) {
        logger.error("ERROR: Failed to convert JSON to object", e);
      }
    });
    return quotes;
  }

  public Optional<String> executeHttpGet(String url) {
    try {
      HttpResponse resp = getHttpClient().execute(new HttpGet(url));
      String entity = EntityUtils.toString(resp.getEntity());
      return Optional.of(entity);
    } catch (IOException e) {
      logger.error("Could not get response from " + url + '\n' + e.getMessage());
      return Optional.empty();
    }
  }
  public HttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        .setConnectionManagerShared(true)
        .build();
  }


  //  TODO
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public List<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote entity) {
    throw new UnsupportedOperationException("Not implemented");
  }
  public void deleteAll(Iterable<? extends IexQuote> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public IexQuote save(IexQuote entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
//  public List<IexQuote> saveAll(List<IexQuote> entities) {
//    throw new UnsupportedOperationException("Not implemented");
//  }
}
