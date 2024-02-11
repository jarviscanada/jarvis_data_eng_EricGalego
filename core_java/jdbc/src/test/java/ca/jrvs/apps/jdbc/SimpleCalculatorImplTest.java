package ca.jrvs.apps.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleCalculatorImplTest {

  SimpleCalculator calculator;

  @BeforeEach
  void init() {
    calculator = new SimpleCalculatorImpl();
  }

  @Test
  void test_add() {
    int expected = 2;
    int actual = calculator.add(1, 1);
    assertEquals(expected, actual);
  }

  @Test
  void test_subtract() {
    int expected = 5;
    int actual = calculator.subtract(6, 1);
    assertEquals(expected, actual);
  }

  @Test
  void test_multiply() {
    int expected = 75;
    int actual = calculator.multiply(25, 3);
    assertEquals(expected, actual);
  }

  @Test
  void test_divide() {
    double expected = 5;
    double actual = calculator.divide(60, 12);
    assertEquals(expected, actual);
  }
}