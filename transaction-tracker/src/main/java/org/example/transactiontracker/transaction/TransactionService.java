package org.example.transactiontracker.transaction;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactiontracker.exchangerate.ExchangeRateService;
import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.LimitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.SERIALIZABLE)
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final ExchangeRateService exchangeRateService;


    private final LimitService limitService;


    public void processTransaction(Transaction transaction) {
        log.info("Processing transaction from account: {}", transaction.getAccountFromId());

        Limit limit = limitService.getOrCreateLimit(transaction.getAccountFromId(), transaction.getExpenseCategory());

        BigDecimal transactionUsdAmount = exchangeRateService.calculateTransactionUsdAmount(transaction, limit.getLimitCurrencyShortname());

        updateTransactionsTotalAmount(limit, transactionUsdAmount);

        boolean limitExceeded = isLimitExceeded(
                limit.getTotalAmountOfTransactions(),
                limit.getLimitSum());

        transaction.setLimitExceeded(limitExceeded);
        transaction.setExceededLimit(limit);

        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsWithLimitExceeded(Long accountId) {
        return transactionRepository.findAllWithLimitExceeded(accountId);
    }

    private boolean isLimitExceeded(BigDecimal totalAmount, BigDecimal limitSum) {
        return totalAmount.compareTo(limitSum) > 0;
    }

    private void updateTransactionsTotalAmount(Limit limit, BigDecimal transactionSum) {
        limit.setTotalAmountOfTransactions(limit.getTotalAmountOfTransactions().add(transactionSum));
    }
}
