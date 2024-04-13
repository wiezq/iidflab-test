package org.example.transactiontracker.limit.dto;

import lombok.Data;
import org.example.transactiontracker.limit.Limit;

/**
 * DTO for {@link Limit}
 */
@Data
public class LimitRequest {
    Double limitSum;
    String limitCurrencyShortname;
    String expenseCategory;
    Long accountAccountId;
}