package org.example.transactiontracker.exchangerate.utils;

import org.example.transactiontracker.exchangerate.ExchangeRate;
import org.example.transactiontracker.exchangerate.dto.ExchangeRateDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeRateMapperTest {

    private final ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

    @Test
    public void testMapToEntity() {
        // Given
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setSymbol("USD/RUB");
        dto.setRate("0.85");
        dto.setTimestamp(Instant.now().getEpochSecond());

        // When
        ExchangeRate entity = exchangeRateMapper.mapToEntity(dto);

        // Then
        assertEquals("RUB/USD", entity.getSymbol());
        BigDecimal expectedRate = BigDecimal.ONE.divide(new BigDecimal(dto.getRate()), 6, RoundingMode.HALF_UP);
        assertEquals(0, expectedRate.compareTo(entity.getRate()));

        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochSecond(dto.getTimestamp()), ZoneId.systemDefault()), entity.getDateTime());
    }

    @Test
    public void testMapToEntity2() {
        // Given
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setSymbol("RUB/USD");
        dto.setRate("0.85");
        dto.setTimestamp(Instant.now().getEpochSecond());

        // When
        ExchangeRate entity = exchangeRateMapper.mapToEntity(dto);

        // Then
        assertEquals("RUB/USD", entity.getSymbol());
        BigDecimal expectedRate = new BigDecimal(dto.getRate());
        assertEquals(0, expectedRate.compareTo(entity.getRate()));

        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochSecond(dto.getTimestamp()), ZoneId.systemDefault()), entity.getDateTime());
    }
}
