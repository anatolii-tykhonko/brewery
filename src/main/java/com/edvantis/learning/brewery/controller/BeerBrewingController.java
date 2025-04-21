package com.edvantis.learning.brewery.controller;

import com.edvantis.learning.brewery.model.BeerType;
import com.edvantis.learning.brewery.model.OrderBeer;
import com.edvantis.learning.brewery.service.OrderService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class BeerBrewingController {
    private final OrderService client;


    @GetMapping("/{id}")
    public String checkStatus(@PathVariable String id) {
        return client.checkStatus(id);
    }

    @GetMapping("/{beerType}/{amountInLitres}")
    public ResponseEntity<UUID> orderBeer(@PathVariable String beerType, @PathVariable Integer amountInLitres) {
        Optional<UUID> response = client.createOrder(BeerType.valueOf(beerType), amountInLitres);
        return response.map(uuid -> ResponseEntity
                .accepted()
                .body(uuid)).orElseGet(() -> ResponseEntity
                .accepted().build());
    }
}
