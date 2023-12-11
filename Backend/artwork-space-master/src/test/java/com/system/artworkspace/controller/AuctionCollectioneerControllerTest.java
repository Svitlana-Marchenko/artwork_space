package com.system.artworkspace.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
    @WithMockUser(username = "artist", authorities = "COLLECTIONEER")
    public void testPlaceBid() throws Exception {
         mockMvc.perform(put("/collectioneer/auctions/{id}/placeBid", 1)
                        .param("bidAmount", "150.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.currentBid").value(150.0));
    }

    @Test
    public void testGetCurrentBid_shouldReturn200() throws Exception {
        mockMvc.perform(get("/collectioneer/auctions/{id}/currentBid", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testDisplayCurrentBidOnNonExistingAuction_shouldReturn500() throws Exception {
        mockMvc.perform(get("/auctions/{id}/currentBid", 100))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetArtworkFromAuction() throws Exception {
        mockMvc.perform(get("/collectioneer/auctions/{id}/artwork", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.user").exists());
    }

    @Test
    public void testGetAuctionById() throws Exception {
      mockMvc.perform(get("/collectioneer/auctions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.artwork.id").exists())
                .andExpect(jsonPath("$.currentBid").exists());
    }

    @Test
    public void testHandleException() throws Exception {
        mockMvc.perform(get("/collectioneer/auctions/invalid"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("ERROR")));
    }

}

