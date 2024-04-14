package org.example.transactiontracker.exchangerate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    // set the precision and scale for the rate column
    @Column(precision = 10, scale = 6)
    private BigDecimal rate;
    private LocalDateTime dateTime;
}
