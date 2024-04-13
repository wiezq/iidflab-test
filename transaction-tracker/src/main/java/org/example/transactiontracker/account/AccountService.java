package org.example.transactiontracker.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getOrCreateAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseGet(() -> createNewAccount(accountId));
    }

    private Account createNewAccount(Long accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setTotalAmount(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
