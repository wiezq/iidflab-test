package org.example.transactiontracker.exchangerate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT e FROM ExchangeRate e WHERE e.symbol = :symbol")
    Optional<ExchangeRate> findBySymbol(String symbol);
}