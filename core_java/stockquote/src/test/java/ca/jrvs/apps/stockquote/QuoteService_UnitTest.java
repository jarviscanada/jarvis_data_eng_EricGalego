package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class QuoteService_UnitTest {


    QuoteService quoteService;
    @Mock
    QuoteDao mockQuoteDao;

    @Mock
    QuoteHttpHelper mockHttpHelper;

    @Mock
    Quote mockQuote;

    @BeforeEach
    void init() {
        quoteService = new QuoteService(mockQuoteDao, mockHttpHelper);
    }

    @Test
    void test_valid_ticker() {
        Mockito.when(mockHttpHelper.fetchQuoteInfo("MSFT")).thenReturn(mockQuote);
        Mockito.when(mockQuoteDao.save(mockQuote)).thenReturn(mockQuote);
        quoteService.fetchQuoteDataFromAPI("MSFT");
        verify(mockQuoteDao).save(any());
    }

    @Test
    void test_invalid_ticker() {
        Mockito.when(mockHttpHelper.fetchQuoteInfo("fake")).thenThrow(new IllegalArgumentException());
        quoteService.fetchQuoteDataFromAPI("fake");
        verifyNoInteractions(mockQuoteDao);
    }
}
