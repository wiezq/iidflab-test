package org.example.transactiontracker.transaction;

import org.example.transactiontracker.exchangerate.ExchangeRateService;
import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.LimitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionServiceUnitTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private LimitService limitService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTransactionWhenLimitNotExceeded() {
        // Given
        Transaction transaction = new Transaction();
        transaction.setAccountFromId(1L);
        transaction.setExpenseCategory("Food");
        transaction.setSum(new BigDecimal(100));

        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal(1000));
        limit.setTotalAmountOfTransactions(BigDecimal.ZERO);
        limit.setLimitCurrencyShortname("USD");

        // When
        when(limitService.getOrCreateLimit(anyLong(), anyString())).thenReturn(limit);
        when(exchangeRateService.calculateTransactionUsdAmount(any(Transaction.class), anyString())).thenReturn(BigDecimal.valueOf(100));

        transactionService.processTransaction(transaction);

        // Then
        assertFalse(transaction.isLimitExceeded());
        assertEquals(transaction.getExceededLimit(), limit);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testProcessTransactionWhenLimitExceeded() {
        // Given
        Transaction transaction = new Transaction();
        transaction.setAccountFromId(1L);
        transaction.setExpenseCategory("Food");
        transaction.setSum(new BigDecimal(100));

        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal(50));
        limit.setTotalAmountOfTransactions(BigDecimal.ZERO);
        limit.setLimitCurrencyShortname("USD");

        // When
        when(limitService.getOrCreateLimit(anyLong(), anyString())).thenReturn(limit);
        when(exchangeRateService.calculateTransactionUsdAmount(any(Transaction.class), anyString())).thenReturn(new BigDecimal(100));

        transactionService.processTransaction(transaction);

        // Then
        assertTrue(transaction.isLimitExceeded());
        assertEquals(transaction.getExceededLimit(), limit);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}