package org.example.transactiontracker.limit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
@AllArgsConstructor
public class LimitService {

    private final LimitRepository limitRepository;

    public Limit getOrCreateLimit(Long accountId, String expenseCategory) {
        return limitRepository.findTopByExpenseCategoryAndAccountId(accountId, expenseCategory)
                .orElseGet(() -> createDefaultLimit(accountId, expenseCategory));
    }

    private Limit createDefaultLimit(Long account, String expenseCategory) {
        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal(1000));
        limit.setLimitDateTime(LocalDateTime.now());
        limit.setLimitCurrencyShortname("USD");
        limit.setTotalAmountOfTransactions(new BigDecimal(0));
        limit.setExpenseCategory(expenseCategory);
        limit.setAccountId(account);
        return limitRepository.save(limit);
    }

    public void saveLimit(Limit limit) {
        limit.setLimitDateTime(LocalDateTime.now());
        // find limit before date and get prev total amount
        BigDecimal prevTotalAmount = limitRepository.findTopByAccountIdAndExpenseCategoryAndLimitDateTimeBeforeOrderByLimitDateTimeDesc(
                limit.getAccountId(), limit.getExpenseCategory(), LocalDateTime.now())
                .map(Limit::getTotalAmountOfTransactions)
                .orElse(new BigDecimal(0));
        limit.setTotalAmountOfTransactions(prevTotalAmount.add(limit.getTotalAmountOfTransactions()));
        limitRepository.save(limit);
    }
}
