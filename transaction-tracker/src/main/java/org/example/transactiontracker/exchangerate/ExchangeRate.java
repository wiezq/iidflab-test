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
    @Column(name = "symbol", nullable = false)
    private String symbol;
    // set the precision and scale for the rate column
    @Column(name = "rate", precision = 10, scale = 6, nullable = false)
    private BigDecimal rate;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
}
