package com.edvantis.learning.brewery.controller;

import com.edvantis.learning.brewery.model.BeerType;
import com.edvantis.learning.brewery.model.Order;
import com.edvantis.learning.brewery.service.BeerBrewingClient;
import com.edvantis.learning.brewery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
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
    @Async
    public CompletableFuture<ResponseEntity<Map<String, String>>> orderBeer(@PathVariable String beerType, @PathVariable Integer amountInLitres) {
        UUID id = UUID.randomUUID();
        Order order;
        order = client.createOrder(String.valueOf(id), BeerType.valueOf(beerType), amountInLitres);
        Map<String, String> response = Map.of(
                "status", order.getStatus(),
                "id", order.getId()
        );
        return CompletableFuture.completedFuture(ResponseEntity
                .accepted()
                .body(response));
    }
}
