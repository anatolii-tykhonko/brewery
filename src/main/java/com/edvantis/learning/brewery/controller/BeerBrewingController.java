package com.edvantis.learning.brewery.controller;

import com.edvantis.learning.brewery.model.BeerType;
import com.edvantis.learning.brewery.model.OrderBeer;
import com.edvantis.learning.brewery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class BeerBrewingController {
    private final OrderService client;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @GetMapping("/{id}")
    public String checkStatus(@PathVariable String id) {
        return client.checkStatus(id);
    }

    @GetMapping("/{beerType}/{amountInLitres}")
    public ResponseEntity<String> orderBeer(@PathVariable String beerType, @PathVariable Integer amountInLitres) {
        UUID uuid = UUID.randomUUID();
        try {
            executorService.submit(() -> client.createOrder(BeerType.valueOf(beerType), amountInLitres, uuid));
        } catch (RejectedExecutionException e){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Many Request");
        }
        return ResponseEntity.accepted().body(String.valueOf(uuid));
    }
}
