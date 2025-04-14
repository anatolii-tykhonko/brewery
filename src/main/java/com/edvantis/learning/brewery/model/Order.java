package com.edvantis.learning.brewery.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private String id;
    private String status;
    private BeerType beerType;
    private int amountInLitres;
}
