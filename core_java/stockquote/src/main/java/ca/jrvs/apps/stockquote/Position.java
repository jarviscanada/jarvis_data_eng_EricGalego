package ca.jrvs.apps.stockquote;

public class Position {

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getNumOfShares() {
    return numOfShares;
  }

  public void setNumOfShares(int numOfShares) {
    this.numOfShares = numOfShares;
  }

  public double getValuePaid() {
    return valuePaid;
  }

  public void setValuePaid(double valuePaid) {
    this.valuePaid = valuePaid;
  }

  private String ticker; //id
  private int numOfShares;
  private double valuePaid; //total amount paid for shares

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Position{");
    sb.append("symbol='").append(ticker).append('\'');
    sb.append(", numOfShares='").append(numOfShares).append('\'');
    sb.append(", valuePaid='").append(valuePaid).append('\'');
    sb.append('}');
    return sb.toString();
  }

}