package org.example.transactiontracker.transaction;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactiontracker.account.Account;
import org.example.transactiontracker.account.AccountService;
import org.example.transactiontracker.exchangerate.ExchangeRateService;
import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.LimitService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final ExchangeRateService exchangeRateService;

    private final AccountService accountService;

    private final LimitService limitService;



    public void processTransaction(Transaction transaction) {
        Account accountFrom = accountService.getOrCreateAccount(transaction.getAccountFrom().getAccountId());
        Account accountTo = accountService.getOrCreateAccount(transaction.getAccountTo().getAccountId());


        Limit limit = limitService.getOrCreateLimit(accountFrom, transaction.getExpenseCategory());

        BigDecimal transactionUsdAmount = exchangeRateService.calculateTransactionUsdAmount(transaction, limit.getLimitCurrencyShortname());
        boolean limitExceeded = isLimitExceeded(accountFrom, transactionUsdAmount, limit.getLimitSum());

        log.info("Limit exceeded: {}, Transaction amount {}", limitExceeded, transaction.getSum());

        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setLimitExceeded(limitExceeded);
        if (limitExceeded) {
            transaction.setExceededLimit(limit);
        }
        transactionRepository.save(transaction);

        updateAccountTotalAmount(accountFrom, transaction.getSum());
    }

    private boolean isLimitExceeded(Account account, BigDecimal transactionUsdAmount, BigDecimal limitSum) {
        return account.getTotalAmount().add(transactionUsdAmount).compareTo(limitSum) > 0;
    }

    private void updateAccountTotalAmount(Account account, BigDecimal amount) {
        account.setTotalAmount(account.getTotalAmount().add(amount));
        accountService.saveAccount(account);
    }
}
