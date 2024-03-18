package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PositionService_UnitTest {


    @Mock
    QuoteService mockQuoteService;
    PositionService positionService;
    @Mock
    PositionDao mockPositionDao;


    @Mock
    Quote mockQuote;

    @Mock
    Position mockPosition;

    @BeforeEach
    void init() {
        positionService = new PositionService(mockPositionDao, mockQuoteService);
    }

    @Test
    void test_buy_valid_ticker() {
        Mockito.when(mockQuoteService.fetchQuoteDataFromAPI("MSFT")).thenReturn(Optional.ofNullable(mockQuote));
        Mockito.when(mockQuote.getVolume()).thenReturn(999);
        Mockito.when(mockPositionDao.findById("MSFT")).thenReturn(Optional.ofNullable(mockPosition));


        positionService.buy("MSFT", 100, 32.9);
        verify(mockPositionDao).save(any());
    }

    @Test
    void test_buy_not_enough_stocks() {
        Mockito.when(mockQuoteService.fetchQuoteDataFromAPI("MSFT")).thenReturn(Optional.ofNullable(mockQuote));
        Mockito.when(mockQuote.getVolume()).thenReturn(9999);

        assertThrows(IllegalArgumentException.class, () -> positionService.buy("MSFT", 999999999, 32.9));
    }

    @Test
    void test_buy_invalid_ticker() {
        Mockito.when(mockQuoteService.fetchQuoteDataFromAPI("fake")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> positionService.buy("fake", 99, 12.3));
    }

    @Test
    void test_sell_ticker_exists() {
        Mockito.when(mockPositionDao.findById("MSFT")).thenReturn(Optional.ofNullable(mockPosition));
        positionService.sell("MSFT");
        verify(mockPositionDao).deleteById("MSFT");
    }

    @Test
    void test_sell_ticker_dne() {
        Mockito.when(mockPositionDao.findById("fake")).thenReturn(Optional.empty());
        positionService.sell("fake");
        verify(mockPositionDao, never()).deleteById("fake");
    }

    // These last two cases may not be needed
    @Test
    void test_find_valid() {
        Mockito.when(mockPositionDao.findById("MSFT")).thenReturn(Optional.ofNullable(mockPosition));
        positionService.findbyId("MSFT");
        verify(mockPositionDao).findById("MSFT");
    }

    @Test
    void test_find_invalid() {
        Mockito.when(mockPositionDao.findById("fake")).thenReturn(Optional.empty());
        assertEquals(positionService.findbyId("fake"), Optional.empty());
    }

}
