package com.edvantis.learning.brewery.service;

import com.edvantis.learning.brewery.DAO.OrderRepository;
import com.edvantis.learning.brewery.error.QueueException;
import com.edvantis.learning.brewery.model.BeerType;
import com.edvantis.learning.brewery.model.OrderBeer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository repository;
    @Autowired
    private final BeerBrewingClient brewingClient;

    public OrderBeer getById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public List<OrderBeer> getAll() {
        return repository.findAll().stream().toList();
    }

    public void updateStatus(String id, String status) {
        repository.updateStatusById(id, status);
    }

    public void save(OrderBeer orderBeer) {
        repository.save(orderBeer);
    }

    public void remove(OrderBeer orderBeer) {
        repository.save(orderBeer);
    }

    @Async
    public void createOrder(BeerType beerType, int amountLiters, UUID id) {
        String status = "IN_PROGRESS";
        OrderBeer orderBeerToSave;
        orderBeerToSave = OrderBeer.builder()
                .id(String.valueOf(id))
                .amountInLitres(amountLiters)
                .beerType(beerType)
                .status(status)
                .build();
        if (brewingClient.isBeerBrewed(beerType)) {
            status = "QUEUED";
            orderBeerToSave.setStatus(status);
            save(orderBeerToSave);
        }
        try {
            brewingClient.brew(beerType, amountLiters);
            orderBeerToSave.setStatus("COMPLETED");
        } catch (IllegalStateException e) {
            orderBeerToSave.setStatus("SPOILED");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        save(orderBeerToSave);
    }


    public String checkStatus(String id) {
        return getById(id).getStatus();
    }
}
