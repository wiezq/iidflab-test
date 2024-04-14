package org.example.transactiontracker.exchangerate.utils;

import org.example.transactiontracker.exchangerate.ExchangeRate;
import org.example.transactiontracker.exchangerate.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ExchangeRateMapper {

    public ExchangeRate mapToEntity(ExchangeRateDto exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate();
        // Данные приходят в виде USD/{symbol}, а нам нужно наоборот
        if (exchangeRateDTO.getSymbol().startsWith("USD/")) {
            BigDecimal dtoRate = BigDecimal.ONE.divide(new BigDecimal(exchangeRateDTO.getRate()), 6, RoundingMode.HALF_UP);
            exchangeRate.setRate(dtoRate);

            String[] symbolParts = exchangeRateDTO.getSymbol().split("/");
            exchangeRate.setSymbol(symbolParts[1] + "/" + symbolParts[0]);
        } else {
            exchangeRate.setRate(new BigDecimal(exchangeRateDTO.getRate()));
            exchangeRate.setSymbol(exchangeRateDTO.getSymbol());
        }
        // timestamp to localdatetime
        Instant instant = Instant.ofEpochSecond(exchangeRateDTO.getTimestamp());
        exchangeRate.setDateTime(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        return exchangeRate;
    }
}
