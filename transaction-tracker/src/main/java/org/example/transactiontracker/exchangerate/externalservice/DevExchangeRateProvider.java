package org.example.transactiontracker.exchangerate.externalservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactiontracker.exchangerate.ExchangeRate;
import org.example.transactiontracker.exchangerate.ExchangeRateRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Profile("dev")
@Slf4j
@AllArgsConstructor
public class DevExchangeRateProvider implements ExchangeRateProvider{

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public void fetchExchangeRate() {
        saveExchangeRate(new BigDecimal("0.0110"), "RUB/USD");

        saveExchangeRate(new BigDecimal("0.0022"), "KZT/USD");
    }

    public void saveExchangeRate(BigDecimal rate, String symbol) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(rate);
        exchangeRate.setSymbol(symbol);
        exchangeRate.setDateTime(LocalDateTime.now());
        exchangeRateRepository.save(exchangeRate);
    }
}
