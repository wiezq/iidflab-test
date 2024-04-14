package org.example.transactiontracker.exchangerate.externalservice;

import lombok.extern.slf4j.Slf4j;
import org.example.transactiontracker.exchangerate.ExchangeRate;
import org.example.transactiontracker.exchangerate.ExchangeRateRepository;
import org.example.transactiontracker.exchangerate.utils.ExchangeRateMapper;
import org.example.transactiontracker.exchangerate.dto.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Profile("prod")
@Slf4j
public class ProdExchangeRateProvider implements ExchangeRateProvider{

    @Value("${exchange_api.url}")
    private String API_URL;

    @Value("${exchange_api.key}")
    private String API_KEY;

    @Value("${exchange_api.symbols}")
    private String SYMBOLS;

    private final ExchangeRateRepository exchangeRateRepository;

    private final ExchangeRateMapper exchangeRateMapper;

    private  WebClient webClient;

    public ProdExchangeRateProvider(ExchangeRateRepository exchangeRateRepository,
                                    ExchangeRateMapper exchangeRateMapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    @Override
    public void fetchExchangeRate() {
        log.info("Fetching exchange rate");
        webClient = WebClient.create(API_URL);
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("symbol", SYMBOLS)
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                // Данные приходят в виде USD/{symbol}, а нам нужно наоборот
                .doOnNext(exchangeRateResponse -> exchangeRateResponse.getRates().values().forEach(exchangeRateDto -> {
                    ExchangeRate exchangeRate = exchangeRateMapper.mapToEntity(exchangeRateDto);
                    log.info("Saving exchange rate: {}", exchangeRate);
                    exchangeRateRepository.save(exchangeRate);
                }))
                .subscribe();

    }




}
