package org.example.transactiontracker.exchangerate;

import lombok.AllArgsConstructor;
import org.example.transactiontracker.exchangerate.externalservice.ExchangeRateProvider;
import org.example.transactiontracker.transaction.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateProvider exchangeRateProvider;

    private final ExchangeRateRepository exchangeRateRepository;


    public BigDecimal calculateTransactionUsdAmount(Transaction transaction, String limitCurrencyShortname) {
        if (transaction.getCurrencyShortname().equals("USD"))
            return transaction.getSum();

        String symbol = transaction.getCurrencyShortname() + "/USD";

        ExchangeRate exchangeRate = findExchangeRateBySymbol(symbol);

        return transaction.getSum()
                .multiply(exchangeRate.getRate()).setScale(2, RoundingMode.HALF_UP);
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void fetchExchangeRate() {
        exchangeRateProvider.fetchExchangeRate();
    }


    public ExchangeRate findExchangeRateBySymbol(String symbol) {
        return exchangeRateRepository.findBySymbol(symbol).orElseThrow(() -> new IllegalArgumentException("Exchange rate not found"));
    }
}
