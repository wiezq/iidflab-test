package org.example.transactiontracker.limit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.transactiontracker.account.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "limits")
@Getter
@Setter
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_sum", nullable = false)
    private BigDecimal limitSum;

    @Column(name = "transaction_sum", nullable = false)
    private BigDecimal transactionSum;

    @Column(name = "limit_datetime", nullable = false)
    private LocalDateTime limitDateTime;

    @Column(name = "limit_currency_shortname", nullable = false)
    private String limitCurrencyShortname;

    @Column(name = "expense_category", nullable = false)
    private String expenseCategory;

    @ManyToOne
    private Account account;
}
