package com.example.demounitech;

import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CurrencyRateService {

    @Autowired
    private CurrencyRateRepository repository;

    public Double fetchRateFromThirdParty(String currencyPair) {

        return Math.random() * 10;
    }

    @Cacheable(value = "currencyRates", key = "#currencyPair")
    public Double getRateForCurrencyPair(String currencyPair) {
        Optional<CurrencyRate> optionalRate = repository.findByCurrencyPair(Boolean.valueOf(currencyPair));

        if (optionalRate.isPresent()) {
            return optionalRate.get().getRate();
        }

        Double rate = fetchRateFromThirdParty(currencyPair);

        CurrencyRate newRate = new CurrencyRate(currencyPair, rate, LocalDateTime.now());
        repository.save(newRate);

        return rate;
    }
}

