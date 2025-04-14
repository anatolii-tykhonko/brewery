package com.edvantis.learning.brewery.controller;

import com.edvantis.learning.brewery.model.BeerType;
import com.edvantis.learning.brewery.service.BeerBrewingClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerBrewingController.class)
class BeerBrewingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BeerBrewingClient brewingService;
    @Test
    void givenOneLitreWhenTypeBeerExistAndOrderOneLitreThenAcceptedStatusAndUUID() throws Exception {
        Mockito.when(brewingService.brew(BeerType.LAGER, 1)).thenReturn(1);

        mockMvc.perform(post("/order")
                        .param("beerType", "Lager")
                        .param("amountInLitres", "1"))
                .andExpect(status().isAccepted());


    }
}