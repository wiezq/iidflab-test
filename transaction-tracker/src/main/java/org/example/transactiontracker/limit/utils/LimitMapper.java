package org.example.transactiontracker.limit.utils;

import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.dto.LimitRequest;
import org.example.transactiontracker.limit.dto.LimitResponse;
import org.springframework.stereotype.Component;

@Component
public class LimitMapper {

    public Limit toEntity(LimitRequest limitRequest) {
        Limit limit = new Limit();
        limit.setLimitSum(limitRequest.getLimitSum());
        limit.setLimitCurrencyShortname(limitRequest.getLimitCurrencyShortname());
        limit.setExpenseCategory(limitRequest.getExpenseCategory());
        limit.setAccountId(limitRequest.getAccountId());
        return limit;
    }

    public LimitResponse toResponse(Limit exceededLimit) {
        LimitResponse limitResponse = new LimitResponse();
        limitResponse.setLimitSum(exceededLimit.getLimitSum());
        limitResponse.setCurrencyShortname(exceededLimit.getLimitCurrencyShortname());
        limitResponse.setDateTime(exceededLimit.getLimitDateTime());
        return limitResponse;
    }
}
