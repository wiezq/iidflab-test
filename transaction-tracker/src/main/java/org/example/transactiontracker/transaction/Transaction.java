package org.example.transactiontracker.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.transactiontracker.limit.Limit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_from", nullable = false)
    private Long accountFromId;

    @Column(name = "account_to", nullable = false)
    private Long accountToId;

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
