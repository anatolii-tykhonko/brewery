package com.edvantis.learning.brewery.service;

import com.edvantis.learning.brewery.model.BeerType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class BeerBrewingClient {

    private static final Set<BeerType> brewingBeerTypes = Collections.synchronizedSet(new HashSet<>());

    /**
     * Brews a batch of beer of the given type.
     * Only one batch of beer can be brewed at a time for a given beer type.
     * Some batches may spoil during brewing. you have to handle this case.
     *
     * @param beerType       the type of beer to brew
     * @param amountInLitres the amount of beer to brew, in litres
     * @return the amount of beer brewed, in litres
     * @throws IllegalAccessException if a batch of beer of the given type is already being brewed
     * @throws IllegalStateException  if the batch of beer spoils during brewing
     */
    public int brew(BeerType beerType, int amountInLitres) throws IllegalAccessException, IllegalStateException {
        synchronized (brewingBeerTypes) {
            if (brewingBeerTypes.contains(beerType)) {
                throw new IllegalAccessException(beerType + " is already being brewed!");
            }
            brewingBeerTypes.add(beerType);
        }

        try {
            System.out.println("Brewing " + amountInLitres + " litres of " + beerType);
            Thread.sleep(amountInLitres * 100L);

            if (new Random().nextInt(10) == 0) {
                throw new IllegalStateException("Batch spoiled!");
            }

            System.out.println("Finished brewing " + amountInLitres + " litres of " + beerType);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            synchronized (brewingBeerTypes) {
                brewingBeerTypes.remove(beerType);
            }
        }
        return amountInLitres;
    }

    public boolean isBeerBrewed(BeerType beerType) {
        return brewingBeerTypes.contains(beerType);
    }
}
