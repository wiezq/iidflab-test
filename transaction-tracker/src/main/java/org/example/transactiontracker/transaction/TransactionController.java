package org.example.transactiontracker.transaction;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping("/process")
    public ResponseEntity<?> processTransaction(@RequestBody TransactionDto transactionDto){
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
}
