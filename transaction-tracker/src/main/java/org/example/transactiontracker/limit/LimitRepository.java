package org.example.transactiontracker.limit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Query("SELECT l FROM Limit l WHERE l.expenseCategory = :expenseCategory AND l.account = :accountId ORDER BY l.limitDateTime DESC")
    Optional<Limit> findTopByExpenseCategoryAndAccountId(@Param("accountId") Long accountId, @Param("expenseCategory") String expenseCategory);}