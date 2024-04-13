package org.example.transactiontracker.limit;

import org.example.transactiontracker.account.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class LimitService {
    private final LimitRepository limitRepository;

    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    public Limit getOrCreateLimit(Account account, String expenseCategory) {
        return limitRepository.findTopByExpenseCategoryAndAccountId(account.getAccountId(), expenseCategory)
                .orElseGet(() -> createDefaultLimit(account, expenseCategory));
    }

    private Limit createDefaultLimit(Account account, String expenseCategory) {
        Limit limit = new Limit();
        limit.setLimitSum(new BigDecimal(1000));
        limit.setLimitDateTime(LocalDateTime.now());
        limit.setLimitCurrencyShortname("USD");
        limit.setExpenseCategory(expenseCategory);
        limit.setAccount(account);
        return limitRepository.save(limit);
    }

    public void saveLimit(Limit limit) {
        limitRepository.save(limit);
    }
}
