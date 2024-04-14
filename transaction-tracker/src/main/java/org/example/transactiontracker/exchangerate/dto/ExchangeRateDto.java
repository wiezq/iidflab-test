package org.example.transactiontracker.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeRateDto {
    private String symbol;
    private long timestamp;
    @JsonProperty("close")
    private String rate;
}
