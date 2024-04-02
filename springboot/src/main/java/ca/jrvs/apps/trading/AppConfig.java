package ca.jrvs.apps.trading;


import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
  private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://api.iex.cloud/v1/");
    marketDataConfig.setToken(System.getenv("IEX_TOKEN"));
    return marketDataConfig;
  }

  @Bean
  public HttpClientConnectionManager httpClientConnectionManagerConfig() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }
}
