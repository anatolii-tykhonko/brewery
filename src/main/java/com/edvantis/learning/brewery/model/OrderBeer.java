package com.edvantis.learning.brewery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "beer order")
public class OrderBeer {
    @Id
    private String id;
    private String status;
    private BeerType beerType;
    private int amountInLitres;
}
