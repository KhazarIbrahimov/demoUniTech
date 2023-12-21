package com.example.demounitech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyRateController {

    @Autowired
    private CurrencyRateService service;

    @GetMapping("/{currencyPair}")
    public ResponseEntity<Double> getCurrencyRate(@PathVariable String currencyPair) {
        Double rate = service.getRateForCurrencyPair(currencyPair);

        if (rate == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rate);
    }
}

