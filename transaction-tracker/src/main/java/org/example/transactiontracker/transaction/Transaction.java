package org.example.transactiontracker.transaction;

import jakarta.persistence.*;
import org.example.transactiontracker.account.Account;
import org.example.transactiontracker.limit.Limit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_from", nullable = false)
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to", nullable = false)
    private Account accountTo;

    @Column(name = "currency_shortname", nullable = false)
    private String currencyShortname;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;

    @Column(name = "expense_category", nullable = false)
    private String expenseCategory;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "limit_exceeded", nullable = false)
    private boolean isLimitExceeded;

    @ManyToOne
    @JoinColumn(name = "limit_id", nullable = true)
    private Limit exceededLimit;
}
