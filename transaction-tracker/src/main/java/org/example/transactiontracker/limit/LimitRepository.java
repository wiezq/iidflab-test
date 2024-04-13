package org.example.transactiontracker.limit;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Limit l WHERE l.expenseCategory = :expenseCategory " +
            "AND l.accountId = :accountId ORDER BY l.limitDateTime DESC LIMIT 1")
    Optional<Limit> findTopByExpenseCategoryAndAccountId(@Param("accountId") Long accountId,
                                                         @Param("expenseCategory") String expenseCategory);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Limit l WHERE l.accountId = :accountId " +
            "AND l.expenseCategory = :expenseCategory" +
            " AND l.limitDateTime < :now ORDER BY l.limitDateTime DESC LIMIT 1")
    Optional<Limit> findLatestLimitBeforeDate(@Param("accountId") Long accountId,
                                              @Param("expenseCategory") String expenseCategory,
                                              @Param("now") LocalDateTime now);
}