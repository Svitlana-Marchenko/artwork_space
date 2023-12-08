package com.system.artworkspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AuctionCollectioneerControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void testGetAvailableAuctions() throws Exception {
        // Implement your test logic for getting available auctions for a collectioneer
        // You may need to mock the AuctionCollectioneerService and define its behavior

        mockMvc.perform(get("/collectioneer/auctions/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].artwork.id").exists())
                .andExpect(jsonPath("$[0].currentBid").exists());
    }

    @Test
    public void testPlaceBid() throws Exception {
        // Implement your test logic for placing a bid on an auction
        // You may need to mock the AuctionCollectioneerService and define its behavior

        mockMvc.perform(put("/collectioneer/auctions/{id}/placeBid", 1)
                        .param("bidAmount", "150.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.currentBid").value(150.0));
    }

    @Test
    public void testGetCurrentBid() throws Exception {
        // Implement your test logic for retrieving the current bid for an auction
        // You may need to mock the AuctionCollectioneerService and define its behavior

        mockMvc.perform(get("/collectioneer/auctions/{id}/currentBid", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testGetArtworkFromAuction() throws Exception {
        // Implement your test logic for retrieving artwork from an auction
        // You may need to mock the AuctionCollectioneerService and define its behavior

        mockMvc.perform(get("/collectioneer/auctions/{id}/artwork", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.artist").exists());
    }

    @Test
    public void testGetAuctionById() throws Exception {
        // Implement your test logic for retrieving an auction by ID
        // You may need to mock the AuctionCollectioneerService and define its behavior

        mockMvc.perform(get("/collectioneer/auctions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.artwork.id").exists())
                .andExpect(jsonPath("$.currentBid").exists());
    }

    @Test
    public void testHandleException() throws Exception {
        // Implement your test logic for handling exceptions in the controller
        // You may need to mock the AuctionCollectioneerService to throw an exception

        mockMvc.perform(get("/collectioneer/auctions/invalid"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("ERROR")));
    }

    // Add more tests for other methods in the AuctionCollectioneerController

}

