package ca.jrvs.apps.jdbc;

public class NotSoSimpleCalculatorImpl implements NotSoSimpleCalculator {
  private SimpleCalculator calc;

  public NotSoSimpleCalculatorImpl(SimpleCalculator calc) {
    this.calc = calc;
  }

  @Override
  public int power(int x, int y) {
    return (int) Math.pow(x, y);
  }

  @Override
  public int abs(int x) {
    if(x < 0)
      return calc.multiply(x, -1);
    return x;
  }

  @Override
  public double sqrt(int x) {
    return Math.sqrt(x);
  }
}
