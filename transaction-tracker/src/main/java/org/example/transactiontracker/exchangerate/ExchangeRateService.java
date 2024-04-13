package org.example.transactiontracker.exchangerate;

import org.example.transactiontracker.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeRateService {
    public BigDecimal calculateTransactionUsdAmount(Transaction transaction, String limitCurrencyShortname) {
        // TODO: implement exchange rate calculation
        if (limitCurrencyShortname.equals("USD"))
            return transaction.getSum();
        return transaction.getSum().multiply(new BigDecimal(2));
    }
}
