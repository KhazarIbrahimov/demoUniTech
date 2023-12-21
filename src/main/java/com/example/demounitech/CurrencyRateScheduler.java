package com.example.demounitech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateScheduler {

    @Autowired
    private CurrencyRateService service;

    @Scheduled(fixedRate = 60000) // Update 1 min.
    public void updateRates() {}
}

