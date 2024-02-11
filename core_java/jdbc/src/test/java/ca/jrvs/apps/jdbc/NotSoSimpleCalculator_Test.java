package ca.jrvs.apps.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
  class NotSoSimpleCalculator_Test {

    NotSoSimpleCalculator calc;

    @Mock
    SimpleCalculator mockSimpleCalc;

    @BeforeEach
    void init() {
      calc = new NotSoSimpleCalculatorImpl(mockSimpleCalc);
    }

    @Test
    void test_power() {
      int expected = 25;
      int actual = calc.power(5, 2);
      assertEquals(expected, actual);
    }

    @Test
    void test_abs() {
      int expected = 10;
      int actual = calc.abs(10);
      assertEquals(expected, actual);
    }

    @Test
  void test_abs2() {
    int expected = 10;
    Mockito.when(mockSimpleCalc.multiply(-10, -1)).thenReturn(10);
    int actual = calc.abs(-10);
    assertEquals(expected, actual);
  }

    @Test
    void test_sqrt() {
      //write your test here
      double expected = 25;
      double actual = calc.sqrt(625);
      assertEquals(expected, actual);
    }
    
}
