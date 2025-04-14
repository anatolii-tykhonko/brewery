package com.edvantis.learning.brewery.service;

import com.edvantis.learning.brewery.DAO.OrderRepository;
import com.edvantis.learning.brewery.model.BeerType;
import com.edvantis.learning.brewery.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository repository;
    @Autowired
    private final BeerBrewingClient brewingClient;

    public Order getById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Order> getAll() {
        return repository.findAll().stream().toList();
    }

    public void save(Order order) {
        repository.save(order);
    }

    public void remove(Order order) {
        repository.save(order);
    }

    public Order createOrder(String id, BeerType beerType, int amountLiters) {
        int result;
        Order orderToSave;
        try {
            result = brewingClient.brew(beerType, amountLiters);
            orderToSave = Order.builder()
                    .id(id)
                    .amountInLitres(amountLiters)
                    .beerType(beerType)
                    .status("started")
                    .build();
            save(orderToSave);
        } catch (IllegalAccessException e) {
            orderToSave = Order.builder()
                    .id(id)
                    .amountInLitres(amountLiters)
                    .beerType(beerType)
                    .status("queued")
                    .build();
            save(orderToSave);
        }
        return orderToSave;
    }


    public String checkStatus(String id) {
        return null;
    }
}
