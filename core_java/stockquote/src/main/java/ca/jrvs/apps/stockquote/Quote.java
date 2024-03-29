package ca.jrvs.apps.stockquote;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.sql.Date;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "01. symbol",
    "02. open",
    "03. high",
    "04. low",
    "05. price",
    "06. volume",
    "07. latest trading day",
    "08. previous close",
    "09. change",
    "10. change percent",
})

public class Quote {

  public Quote(String ticker, double open, double high, double low, double price, int volume,
      Date latestTradingDay, double previousClose, double change, String changePercent,
      Timestamp timestamp) {
    this.ticker = ticker;
    this.open = open;
    this.high = high;
    this.low = low;
    this.price = price;
    this.volume = volume;
    this.latestTradingDay = latestTradingDay;
    this.previousClose = previousClose;
    this.change = change;
    this.changePercent = changePercent;
    this.timestamp = timestamp;
  }

  public Quote() {
  }

  @JsonProperty("01. symbol")
  public String getTicker() {
    return ticker;
  }

  @JsonProperty("01. symbol")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @JsonProperty("02. open")
  public double getOpen() {
    return open;
  }

  @JsonProperty("02. open")
  public void setOpen(double open) {
    this.open = open;
  }

  @JsonProperty("03. high")
  public double getHigh() {
    return high;
  }

  @JsonProperty("03. high")
  public void setHigh(double high) {
    this.high = high;
  }

  @JsonProperty("04. low")
  public double getLow() {
    return low;
  }

  @JsonProperty("04. low")
  public void setLow(double low) { this.low = low; }

  @JsonProperty("05. price")
  public double getPrice() {
    return price;
  }

  @JsonProperty("05. price")
  public void setPrice(double price) {
    this.price = price;
  }

  @JsonProperty("06. volume")
  public int getVolume() {
    return volume;
  }

  @JsonProperty("06. volume")
  public void setVolume(int volume) {
    this.volume = volume;
  }

  @JsonProperty("07. latest trading day")
  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  @JsonProperty("07. latest trading day")
  public void setLatestTradingDay(Date latestTradingDay) {
    this.latestTradingDay = latestTradingDay;
  }

  @JsonProperty("08. previous close")
  public double getPreviousClose() {
    return previousClose;
  }

  @JsonProperty("08. previous close")
  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  @JsonProperty("09. change")
  public double getChange() {
    return change;
  }

  @JsonProperty("09. change")
  public void setChange(double change) {
    this.change = change;
  }

  @JsonProperty("10. change percent")
  public String getChangePercent() {
    return changePercent;
  }

  @JsonProperty("10. change percent")
  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @JsonProperty("01. symbol")
  private String ticker; //id

  @JsonProperty("02. open")
  private double open;

  @JsonProperty("03. high")
  private double high;

  @JsonProperty("04. low")
  private double low;

  @JsonProperty("05. price")
  private double price;

  @JsonProperty("06. volume")
  private int volume;

  @JsonProperty("07. latest trading day")
  private Date latestTradingDay;

  @JsonProperty("08. previous close")
  private double previousClose;

  @JsonProperty("09. change")
  private double change;

  @JsonProperty("10. change percent")
  private String changePercent;

  private Timestamp timestamp; //time when the info was pulled


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Stock='").append(ticker).append("'\n");
    sb.append("Open='").append(open).append("'\n");
    sb.append("High='").append(high).append("'\n");
    sb.append("Low='").append(low).append("'\n");
    sb.append("Price='").append(price).append("'\n");
    sb.append("Volume='").append(volume).append("'\n");
    sb.append("Latest Trading Day='").append(latestTradingDay).append("'\n");
    sb.append("Previous Close='").append(previousClose).append("'\n");
    sb.append("Change='").append(change).append("'\n");
    sb.append("Change Percent='").append(changePercent).append("'\n");
    sb.append("Timestamp='").append(timestamp).append("'\n");
    return sb.toString();
  }
}

