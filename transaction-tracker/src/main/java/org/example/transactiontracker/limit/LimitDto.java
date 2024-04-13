package org.example.transactiontracker.limit;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Limit}
 */
@Data
public class LimitDto {
    Double limitSum;
    String limitCurrencyShortname;
    String expenseCategory;
    Long accountAccountId;
}