package org.example.transactiontracker.transaction.dto;

import lombok.Value;
import org.example.transactiontracker.transaction.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Transaction}
 */
@Value
public class TransactionRequest implements Serializable {
    Long accountFromAccountId;
    Long accountToAccountId;
    String currencyShortname;
    BigDecimal sum;
    String expenseCategory;
}