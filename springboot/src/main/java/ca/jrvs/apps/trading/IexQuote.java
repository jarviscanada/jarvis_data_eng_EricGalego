package ca.jrvs.apps.trading;

public class IexQuote {
  private String symbol;
  private String companyName;
  private String primaryExchange;
  private String calculationPrice;
  private Double open;
  private Long openTime;
  private String openSource;
  private Double close;
  private Long closeTime;
  private String closeSource;
  private Double high;
  private Long highTime;
  private String highSource;
  private Double low;
  private Long lowTime;
  private String lowSource;
  private Double latestPrice;
  private String latestSource;
  private String latestTime;
  private Long latestUpdate;
  private Integer latestVolume;
  private Double iexRealtimePrice;
  private Integer iexRealtimeSize;
  private Long iexLastUpdated;
  private Double delayedPrice;
  private Long delayedPriceTime;
  private Double oddLotDelayedPrice;
  private Long oddLotDelayedPriceTime;
  private Double extendedPrice;
  private Double extendedChange;
  private Double extendedChangePercent;
  private Long extendedPriceTime;
  private Double previousClose;
  private Integer previousVolume;
  private Double change;
  private Double changePercent;
  private Integer volume;
  private Double iexMarketPercent;
  private Integer iexVolume;
  private Integer avgTotalVolume;
  private Integer iexBidPrice;
  private Integer iexBidSize;
  private Integer iexAskPrice;
  private Integer iexAskSize;
  private Double iexOpen;
  private Long iexOpenTime;
  private Double iexClose;
  private Long iexCloseTime;
  private Long marketCap;
  private Double peRatio;
  private Double week52High;
  private Double week52Low;
  private Double ytdChange;
  private Long lastTradeTime;
  private String currency;
  private Boolean isUSMarketOpen;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getPrimaryExchange() {
    return primaryExchange;
  }

  public void setPrimaryExchange(String primaryExchange) {
    this.primaryExchange = primaryExchange;
  }

  public String getCalculationPrice() {
    return calculationPrice;
  }

  public void setCalculationPrice(String calculationPrice) {
    this.calculationPrice = calculationPrice;
  }

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Long getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Long openTime) {
    this.openTime = openTime;
  }

  public String getOpenSource() {
    return openSource;
  }

  public void setOpenSource(String openSource) {
    this.openSource = openSource;
  }

  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  public Long getCloseTime() {
    return closeTime;
  }

  public void setCloseTime(Long closeTime) {
    this.closeTime = closeTime;
  }

  public String getCloseSource() {
    return closeSource;
  }

  public void setCloseSource(String closeSource) {
    this.closeSource = closeSource;
  }

  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Long getHighTime() {
    return highTime;
  }

  public void setHighTime(Long highTime) {
    this.highTime = highTime;
  }

  public String getHighSource() {
    return highSource;
  }

  public void setHighSource(String highSource) {
    this.highSource = highSource;
  }

  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  public Long getLowTime() {
    return lowTime;
  }

  public void setLowTime(Long lowTime) {
    this.lowTime = lowTime;
  }

  public String getLowSource() {
    return lowSource;
  }

  public void setLowSource(String lowSource) {
    this.lowSource = lowSource;
  }

  public Double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(Double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public String getLatestSource() {
    return latestSource;
  }

  public void setLatestSource(String latestSource) {
    this.latestSource = latestSource;
  }

  public String getLatestTime() {
    return latestTime;
  }

  public void setLatestTime(String latestTime) {
    this.latestTime = latestTime;
  }

  public Long getLatestUpdate() {
    return latestUpdate;
  }

  public void setLatestUpdate(Long latestUpdate) {
    this.latestUpdate = latestUpdate;
  }

  public Integer getLatestVolume() {
    return latestVolume;
  }

  public void setLatestVolume(Integer latestVolume) {
    this.latestVolume = latestVolume;
  }

  public Double getIexRealtimePrice() {
    return iexRealtimePrice;
  }

  public void setIexRealtimePrice(Double iexRealtimePrice) {
    this.iexRealtimePrice = iexRealtimePrice;
  }

  public Integer getIexRealtimeSize() {
    return iexRealtimeSize;
  }

  public void setIexRealtimeSize(Integer iexRealtimeSize) {
    this.iexRealtimeSize = iexRealtimeSize;
  }

  public Long getIexLastUpdated() {
    return iexLastUpdated;
  }

  public void setIexLastUpdated(Long iexLastUpdated) {
    this.iexLastUpdated = iexLastUpdated;
  }

  public Double getDelayedPrice() {
    return delayedPrice;
  }

  public void setDelayedPrice(Double delayedPrice) {
    this.delayedPrice = delayedPrice;
  }

  public Long getDelayedPriceTime() {
    return delayedPriceTime;
  }

  public void setDelayedPriceTime(Long delayedPriceTime) {
    this.delayedPriceTime = delayedPriceTime;
  }

  public Double getOddLotDelayedPrice() {
    return oddLotDelayedPrice;
  }

  public void setOddLotDelayedPrice(Double oddLotDelayedPrice) {
    this.oddLotDelayedPrice = oddLotDelayedPrice;
  }

  public Long getOddLotDelayedPriceTime() {
    return oddLotDelayedPriceTime;
  }

  public void setOddLotDelayedPriceTime(Long oddLotDelayedPriceTime) {
    this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
  }

  public Double getExtendedPrice() {
    return extendedPrice;
  }

  public void setExtendedPrice(Double extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public Double getExtendedChange() {
    return extendedChange;
  }

  public void setExtendedChange(Double extendedChange) {
    this.extendedChange = extendedChange;
  }

  public Double getExtendedChangePercent() {
    return extendedChangePercent;
  }

  public void setExtendedChangePercent(Double extendedChangePercent) {
    this.extendedChangePercent = extendedChangePercent;
  }

  public Long getExtendedPriceTime() {
    return extendedPriceTime;
  }

  public void setExtendedPriceTime(Long extendedPriceTime) {
    this.extendedPriceTime = extendedPriceTime;
  }

  public Double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(Double previousClose) {
    this.previousClose = previousClose;
  }

  public Integer getPreviousVolume() {
    return previousVolume;
  }

  public void setPreviousVolume(Integer previousVolume) {
    this.previousVolume = previousVolume;
  }

  public Double getChange() {
    return change;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public Double getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(Double changePercent) {
    this.changePercent = changePercent;
  }

  public Integer getVolume() {
    return volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public Double getIexMarketPercent() {
    return iexMarketPercent;
  }

  public void setIexMarketPercent(Double iexMarketPercent) {
    this.iexMarketPercent = iexMarketPercent;
  }

  public Integer getIexVolume() {
    return iexVolume;
  }

  public void setIexVolume(Integer iexVolume) {
    this.iexVolume = iexVolume;
  }

  public Integer getAvgTotalVolume() {
    return avgTotalVolume;
  }

  public void setAvgTotalVolume(Integer avgTotalVolume) {
    this.avgTotalVolume = avgTotalVolume;
  }

  public Integer getIexBidPrice() {
    return iexBidPrice;
  }

  public void setIexBidPrice(Integer iexBidPrice) {
    this.iexBidPrice = iexBidPrice;
  }

  public Integer getIexBidSize() {
    return iexBidSize;
  }

  public void setIexBidSize(Integer iexBidSize) {
    this.iexBidSize = iexBidSize;
  }

  public Integer getIexAskPrice() {
    return iexAskPrice;
  }

  public void setIexAskPrice(Integer iexAskPrice) {
    this.iexAskPrice = iexAskPrice;
  }

  public Integer getIexAskSize() {
    return iexAskSize;
  }

  public void setIexAskSize(Integer iexAskSize) {
    this.iexAskSize = iexAskSize;
  }

  public Double getIexOpen() {
    return iexOpen;
  }

  public void setIexOpen(Double iexOpen) {
    this.iexOpen = iexOpen;
  }

  public Long getIexOpenTime() {
    return iexOpenTime;
  }

  public void setIexOpenTime(Long iexOpenTime) {
    this.iexOpenTime = iexOpenTime;
  }

  public Double getIexClose() {
    return iexClose;
  }

  public void setIexClose(Double iexClose) {
    this.iexClose = iexClose;
  }

  public Long getIexCloseTime() {
    return iexCloseTime;
  }

  public void setIexCloseTime(Long iexCloseTime) {
    this.iexCloseTime = iexCloseTime;
  }

  public Long getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(Long marketCap) {
    this.marketCap = marketCap;
  }

  public Double getPeRatio() {
    return peRatio;
  }

  public void setPeRatio(Double peRatio) {
    this.peRatio = peRatio;
  }

  public Double getWeek52High() {
    return week52High;
  }

  public void setWeek52High(Double week52High) {
    this.week52High = week52High;
  }

  public Double getWeek52Low() {
    return week52Low;
  }

  public void setWeek52Low(Double week52Low) {
    this.week52Low = week52Low;
  }

  public Double getYtdChange() {
    return ytdChange;
  }

  public void setYtdChange(Double ytdChange) {
    this.ytdChange = ytdChange;
  }

  public Long getLastTradeTime() {
    return lastTradeTime;
  }

  public void setLastTradeTime(Long lastTradeTime) {
    this.lastTradeTime = lastTradeTime;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Boolean getUSMarketOpen() {
    return isUSMarketOpen;
  }

  public void setUSMarketOpen(Boolean USMarketOpen) {
    isUSMarketOpen = USMarketOpen;
  }
}
