package org.example.transactiontracker.limit.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LimitResponse {
    BigDecimal limitSum;
    LocalDateTime dateTime;
    String currencyShortname;
}
