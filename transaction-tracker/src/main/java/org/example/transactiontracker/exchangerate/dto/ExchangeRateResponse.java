package org.example.transactiontracker.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExchangeRateResponse {
    private final Map<String, ExchangeRateDto> rates;

    public ExchangeRateResponse() {
        this.rates = new HashMap<>();
    }

    @JsonAnySetter
    public void setRates(String key, ExchangeRateDto value) {
        rates.put(key, value);
    }

}
