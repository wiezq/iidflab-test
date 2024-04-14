package org.example.transactiontracker.transaction;


import lombok.AllArgsConstructor;
import org.example.transactiontracker.transaction.dto.TransactionRequest;
import org.example.transactiontracker.transaction.dto.TransactionResponse;
import org.example.transactiontracker.transaction.utils.TransactionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper;

    @PostMapping("/process")
    public ResponseEntity<?> processTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionMapper.toEntity(transactionRequest);
        transactionService.processTransaction(transaction);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/limitExceeded")
    public ResponseEntity<?> getAllTransactionsWithLimitExceeded(@RequestParam("accountId") Long accountId) {
        List<TransactionResponse> responseList = transactionService.getAllTransactionsWithLimitExceeded(accountId).stream()
                .map(transactionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responseList);
    }


}
