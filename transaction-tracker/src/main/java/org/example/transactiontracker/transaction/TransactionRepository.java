package org.example.transactiontracker.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t JOIN t.exceededLimit l WHERE " +
            "t.isLimitExceeded = true and t.accountFromId = :accountId")
    List<Transaction> findAllWithLimitExceeded(@Param("accountId") Long accountId);
}