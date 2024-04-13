package org.example.transactiontracker.transaction.dto;

import lombok.Data;
import org.example.transactiontracker.limit.dto.LimitResponse;
import org.example.transactiontracker.transaction.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Transaction}
 */
@Data
public class TransactionResponse {
    Long id;
    Long accountFromId;
    Long accountToId;
    String currencyShortname;
    BigDecimal sum;
    String expenseCategory;
    LocalDateTime dateTime;
    boolean isLimitExceeded;
    LimitResponse exceededLimit;
}