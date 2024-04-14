package org.example.transactiontracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionTrackerApplication.class, args);
	}

}
