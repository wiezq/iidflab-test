package org.example.transactiontracker.limit.utils;

import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.dto.LimitRequest;
import org.example.transactiontracker.limit.dto.LimitResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LimitMapperTest {

    private final LimitMapper limitMapper = new LimitMapper();


    @Test
    public void testToEntity() {
        // Given
        LimitRequest request = new LimitRequest();
        request.setLimitSum(new BigDecimal(1000));
        request.setLimitCurrencyShortname("USD");
        request.setExpenseCategory("Food");
        request.setAccountId(1L);

        // When
        Limit limit = limitMapper.toEntity(request);

        // Then
        assertEquals(request.getLimitSum(), limit.getLimitSum());
        assertEquals(request.getLimitCurrencyShortname(), limit.getLimitCurrencyShortname());
        assertEquals(request.getExpenseCategory(), limit.getExpenseCategory());
        assertEquals(request.getAccountId(), limit.getAccountId());
    }

    @Test
    public void testToResponse(){
        // Given
        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal(1000));
        limit.setLimitCurrencyShortname("USD");
        limit.setLimitDateTime(LocalDateTime.parse("2021-01-01T00:00:00"));

        // When
        LimitResponse response = limitMapper.toResponse(limit);

        // Then
        assertEquals(limit.getLimitSum(), response.getLimitSum());
        assertEquals(limit.getLimitCurrencyShortname(), response.getCurrencyShortname());
        assertEquals(limit.getLimitDateTime(), response.getDateTime());
    }
}
