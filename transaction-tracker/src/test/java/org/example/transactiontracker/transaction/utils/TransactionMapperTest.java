package org.example.transactiontracker.transaction.utils;

import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.transaction.Transaction;
import org.example.transactiontracker.transaction.dto.TransactionRequest;
import org.example.transactiontracker.transaction.dto.TransactionResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionMapperTest {

    private final TransactionMapper transactionMapper = new TransactionMapper();

    @Test
    public void testToEntity() {
        // Given
        TransactionRequest request = new TransactionRequest();
        request.setAccountFromAccountId(1L);
        request.setAccountToAccountId(2L);
        request.setCurrencyShortname("USD");
        request.setSum(new BigDecimal(100));
        request.setExpenseCategory("Food");
        // When
        Transaction transaction = transactionMapper.toEntity(request);
        // Then
        assertEquals(request.getAccountFromAccountId(), transaction.getAccountFromId());
        assertEquals(request.getAccountToAccountId(), transaction.getAccountToId());
        assertEquals(request.getCurrencyShortname(), transaction.getCurrencyShortname());
        assertEquals(request.getSum(), transaction.getSum());
        assertEquals(request.getExpenseCategory(), transaction.getExpenseCategory());
    }

    @Test
    public void testToResponse() {
        // Given
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountFromId(1L);
        transaction.setAccountToId(2L);
        transaction.setCurrencyShortname("USD");
        transaction.setSum(new BigDecimal(100));
        transaction.setExpenseCategory("Food");
        transaction.setDateTime(LocalDateTime.parse("2021-02-01T00:00:00"));
        transaction.setLimitExceeded(true);

        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal(1000));
        limit.setLimitCurrencyShortname("USD");
        limit.setLimitDateTime(LocalDateTime.parse("2021-01-01T00:00:00"));

        transaction.setExceededLimit(limit);

        // When
        TransactionResponse response = transactionMapper.toResponse(transaction);

        // Then
        assertEquals(transaction.getId(), response.getId());
        assertEquals(transaction.getAccountFromId(), response.getAccountFromId());
        assertEquals(transaction.getAccountToId(), response.getAccountToId());
        assertEquals(transaction.getCurrencyShortname(), response.getCurrencyShortname());
        assertEquals(transaction.getSum(), response.getSum());
        assertEquals(transaction.getExpenseCategory(), response.getExpenseCategory());
        assertEquals(transaction.getDateTime(), response.getDateTime());
        assertEquals(transaction.isLimitExceeded(), response.isLimitExceeded());
        assertEquals(limit.getLimitSum(), response.getExceededLimit().getLimitSum());
        assertEquals(limit.getLimitCurrencyShortname(), response.getExceededLimit().getCurrencyShortname());
        assertEquals(limit.getLimitDateTime(), response.getExceededLimit().getDateTime());






    }
}
