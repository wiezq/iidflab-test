package org.example.transactiontracker.limit;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/limit")
@AllArgsConstructor
public class LimitController {

    private final LimitService limitService;


    @PostMapping
    public ResponseEntity<?> createLimit(@RequestBody LimitDto limitDto){

        Limit limit = new Limit();
        limit.setLimitSum(BigDecimal.valueOf(limitDto.getLimitSum()));
        limit.setLimitCurrencyShortname(limitDto.getLimitCurrencyShortname());
        limit.setExpenseCategory(limitDto.getExpenseCategory());
        limit.setAccountId(limitDto.getAccountAccountId());
        limit.setTotalAmountOfTransactions(BigDecimal.ZERO);
        limitService.saveLimit(limit);

        return ResponseEntity.ok().build();
    }
}
