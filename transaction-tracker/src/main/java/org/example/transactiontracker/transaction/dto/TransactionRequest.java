package org.example.transactiontracker.transaction.dto;

import lombok.Data;
import org.example.transactiontracker.transaction.Transaction;

import java.math.BigDecimal;

/**
 * DTO for {@link Transaction}
 */
@Data
public class TransactionRequest {
    Long accountFromAccountId;
    Long accountToAccountId;
    String currencyShortname;
    BigDecimal sum;
    String expenseCategory;
}