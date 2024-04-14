package org.example.transactiontracker.limit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LimitRequest {
    BigDecimal limitSum;
    String limitCurrencyShortname;
    String expenseCategory;
    Long accountId;
}