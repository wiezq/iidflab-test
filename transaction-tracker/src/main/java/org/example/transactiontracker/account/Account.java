package org.example.transactiontracker.account;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "account_number", nullable = false, unique = true)
    private Long accountId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
}
