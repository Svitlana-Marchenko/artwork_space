package com.system.artworkspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.auction.AuctionCollectioneerController;
import com.system.artworkspace.auction.AuctionCollectioneerService;
import com.system.artworkspace.security.auth.jwt.JwtAuthenticationFilter;
import com.system.artworkspace.security.auth.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuctionCollectioneerController.class)
public class AuctionCollectioneerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuctionCollectioneerService auctionCollectioneerService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void testGetAllActiveAuctions_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/collectioneer/auctions/available")
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAuctionsById_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/collectioneer/auctions/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllAuctionsByCustomerId_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/collectioneer/auctions/user/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPlaceBid_shouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/collectioneer/auctions/{id}/placeBid", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(100)))
                .andExpect(status().isOk());
    }

}

