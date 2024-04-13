package org.example.transactiontracker.transaction;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Transaction}
 */
@Value
public class TransactionDto implements Serializable {
    Long accountFromAccountId;
    Long accountToAccountId;
    String currencyShortname;
    BigDecimal sum;
    String expenseCategory;
}