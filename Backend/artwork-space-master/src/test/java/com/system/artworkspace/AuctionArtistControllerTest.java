package com.system.artworkspace;
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

    @Test
    public void testCreateAuction() throws Exception {
        // Implement your test logic for creating an auction
        // You may need to mock the AuctionArtistService and define its behavior

        mockMvc.perform(post("/auctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"artwork\": { \"id\": 1 }, \"startingPrice\": 100.0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testDisplayCurrentBid() throws Exception {
        // Implement your test logic for displaying the current bid for an auction
        // You may need to mock the AuctionArtistService and define its behavior

        mockMvc.perform(get("/auctions/{id}/currentBid", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testDisplayCurrentBuyer() throws Exception {
        // Implement your test logic for displaying the current buyer for an auction
        // You may need to mock the AuctionArtistService and define its behavior

        mockMvc.perform(get("/auctions/{id}/currentBuyer", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void testGetAllActiveAuctions() throws Exception {
        // Implement your test logic for getting all active auctions
        // You may need to mock the AuctionArtistService and define its behavior

        mockMvc.perform(get("/auctions/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].artwork.id").exists())
                .andExpect(jsonPath("$[0].startingPrice").exists());
    }

    @Test
    public void testCloseAuction() throws Exception {
        // Implement your test logic for closing an auction
        // You may need to mock the AuctionArtistService and define its behavior

        mockMvc.perform(put("/auctions/{id}/close", 1))
                .andExpect(status().isOk());
    }

    // Add more tests for other methods in the AuctionArtistController

}
