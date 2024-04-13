package org.example.transactiontracker.transaction;


import lombok.AllArgsConstructor;
import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.dto.LimitResponse;
import org.example.transactiontracker.transaction.dto.TransactionRequest;
import org.example.transactiontracker.transaction.dto.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping("/process")
    public ResponseEntity<?> processTransaction(@RequestBody TransactionRequest transactionDto){
        Transaction transaction = new Transaction();


        transaction.setAccountFromId(transactionDto.getAccountFromAccountId());
        transaction.setAccountToId(transactionDto.getAccountToAccountId());
        transaction.setSum(transactionDto.getSum());
        transaction.setExpenseCategory(transactionDto.getExpenseCategory());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setCurrencyShortname(transactionDto.getCurrencyShortname());
        transactionService.processTransaction(transaction);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/limitExceeded")
    public ResponseEntity<?> getAllTransactionsWithLimitExceeded(@RequestParam("accountId") Long accountId){
        List<TransactionResponse> responseList = transactionService.getAllTransactionsWithLimitExceeded(accountId).stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    public TransactionResponse toResponse(Transaction transaction){
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAccountFromId(transaction.getAccountFromId());
        response.setAccountToId(transaction.getAccountToId());
        response.setCurrencyShortname(transaction.getCurrencyShortname());
        response.setSum(transaction.getSum());
        response.setExpenseCategory(transaction.getExpenseCategory());
        response.setDateTime(transaction.getDateTime());
        response.setLimitExceeded(transaction.isLimitExceeded());

        Limit limit = transaction.getExceededLimit();
        LimitResponse limitResponse = new LimitResponse();
        limitResponse.setLimitSum(limit.getLimitSum());
        limitResponse.setCurrencyShortname(limit.getLimitCurrencyShortname());
        limitResponse.setDateTime(limit.getLimitDateTime());

        response.setExceededLimit(limitResponse);

        return response;
    }
}
