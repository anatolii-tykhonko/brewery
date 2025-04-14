package com.edvantis.learning.brewery.service;

import com.edvantis.learning.brewery.model.BeerType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeerBrewingClientTest {

    @Test
    void givenOneLitreWhenTypeBeerExistAndOrderOneLitreThenReturnOne() throws IllegalAccessException {
        BeerBrewingClient client = new BeerBrewingClient();
        BeerType typeBeer = BeerType.LAGER;
        int expectedAmountLiter = 1;

        int actualAmountLiter = client.brew(typeBeer, expectedAmountLiter);
        assertEquals(expectedAmountLiter, actualAmountLiter);

    }
    @Test
    @Disabled
    void givenTenLitreTwiceWhenTypeBeerExistAndOrderTwiceOneLitreThenThrowIllegalAccessException() throws IllegalAccessException {
        BeerBrewingClient client = new BeerBrewingClient();
        BeerType typeBeer = BeerType.LAGER;
        int amountLiter = 100;

        client.brew(typeBeer, amountLiter);
        assertThrows(IllegalAccessException.class, () -> client.brew(typeBeer, amountLiter));


    }
}