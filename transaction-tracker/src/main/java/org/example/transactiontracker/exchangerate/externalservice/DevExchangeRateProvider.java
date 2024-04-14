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
        ExchangeRate exchangeRateRubUsd = new ExchangeRate();
        exchangeRateRubUsd.setRate(new BigDecimal("0.011"));
        exchangeRateRubUsd.setSymbol("RUB/USD");
        exchangeRateRubUsd.setDateTime(LocalDateTime.now());
        exchangeRateRepository.save(exchangeRateRubUsd);

        ExchangeRate exchangeRateKztUsd = new ExchangeRate();
        exchangeRateRubUsd.setRate(new BigDecimal("0.0022"));
        exchangeRateRubUsd.setSymbol("KZT/USD");
        exchangeRateRubUsd.setDateTime(LocalDateTime.now());
        exchangeRateRepository.save(exchangeRateKztUsd);
    }
}
