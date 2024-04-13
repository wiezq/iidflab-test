package org.example.transactiontracker.limit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.example.transactiontracker.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "limits")
@Getter
@Setter
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "limit_sum", nullable = false)
    private BigDecimal limitSum;

    @Column(name = "transaction_sum", nullable = false)
    private BigDecimal totalAmountOfTransactions;

    @Column(name = "limit_datetime", nullable = false)
    private LocalDateTime limitDateTime;

    @Column(name = "limit_currency_shortname", nullable = false)
    private String limitCurrencyShortname;

    @Column(name = "expense_category", nullable = false)
    private String expenseCategory;

    @OneToMany(mappedBy = "exceededLimit")
    List<Transaction> transactions = new ArrayList<>();

}
