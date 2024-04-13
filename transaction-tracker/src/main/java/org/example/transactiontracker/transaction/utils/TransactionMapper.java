package org.example.transactiontracker.transaction.utils;

import lombok.AllArgsConstructor;
import org.example.transactiontracker.limit.Limit;
import org.example.transactiontracker.limit.dto.LimitResponse;
import org.example.transactiontracker.limit.utils.LimitMapper;
import org.example.transactiontracker.transaction.Transaction;
import org.example.transactiontracker.transaction.dto.TransactionRequest;
import org.example.transactiontracker.transaction.dto.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionMapper {

    private final LimitMapper limitMapper;

    public Transaction toEntity(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setAccountFromId(transactionRequest.getAccountFromAccountId());
        transaction.setAccountToId(transactionRequest.getAccountToAccountId());
        transaction.setCurrencyShortname(transactionRequest.getCurrencyShortname());
        transaction.setSum(transactionRequest.getSum());
        transaction.setExpenseCategory(transactionRequest.getExpenseCategory());

        return transaction;
    }

    public TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAccountFromId(transaction.getAccountFromId());
        response.setAccountToId(transaction.getAccountToId());
        response.setCurrencyShortname(transaction.getCurrencyShortname());
        response.setSum(transaction.getSum());
        response.setExpenseCategory(transaction.getExpenseCategory());
        response.setDateTime(transaction.getDateTime());
        response.setLimitExceeded(transaction.isLimitExceeded());

        LimitResponse limitResponse = limitMapper.toResponse(transaction.getExceededLimit());
        response.setExceededLimit(limitResponse);

        return response;
    }
}
