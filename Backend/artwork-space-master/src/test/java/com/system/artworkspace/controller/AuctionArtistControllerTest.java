package com.system.artworkspace.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
public class AuctionArtistControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

//    @Test
//    public void testCreateAuction() throws Exception {
//        // Implement your test logic for creating an auction
//        // You may need to mock the AuctionArtistService and define its behavior
//
//        mockMvc.perform(post("/auctions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"artwork\": { \"id\": 1 }, \"startingPrice\": 100.0 }"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists());
//    }

    @Test
    public void testDisplayCurrentBid_shouldReturn200() throws Exception {
         mockMvc.perform(get("/auctions/{id}/currentBid", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testDisplayCurrentBidOnNonExistingAuction_shouldReturn500() throws Exception {
        mockMvc.perform(get("/auctions/{id}/currentBid", 100))
                .andExpect(status().is5xxServerError());
    }

//    @Test
//    public void testDisplayCurrentBuyer() throws Exception {
//         mockMvc.perform(get("/auctions/{id}/currentBuyer", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.username").exists())
//                .andExpect(jsonPath("$.email").exists());
//    }

    @Test
    public void testGetAllActiveAuctions_shouldReturn200() throws Exception {
        mockMvc.perform(get("/auctions/active"))
                .andExpect(status().isOk());
    }

}
