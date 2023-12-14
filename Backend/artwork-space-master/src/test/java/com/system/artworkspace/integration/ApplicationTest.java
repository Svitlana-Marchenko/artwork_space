package com.system.artworkspace.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest
public class ApplicationTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAvailableAuctions_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/collectioneer/auctions/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].artwork.id").exists())
                .andExpect(jsonPath("$[0].currentBid").exists());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void testChangePassword_shouldReturn200() throws Exception {
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"oldPassword\": \"password\", \"newPassword\": \"Newpass12345\" }"))
                .andExpect(status().isOk());
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
    public void getExhibitionByCuratorId_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/exhibitions/curator/{curatorId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testDisplayCurrentBid_shouldReturn200() throws Exception {
        mockMvc.perform(get("/auctions/{id}/currentBid", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testChangePasswordWithIncorrectPassword_shouldReturn400() throws Exception {
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"oldPassword\": \"password1\", \"newPassword\": \"Newpass12345\" }"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void testChangePasswordWithIncorrectNewPassword_shouldReturn500() throws Exception {
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"oldPassword\": \"password\", \"newPassword\": \"12345678\" }"))
                .andExpect(status().is5xxServerError()  );
    }

    @Test
    public void testGetCurrentBid_shouldReturn200() throws Exception {
        mockMvc.perform(get("/collectioneer/auctions/{id}/currentBid", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void testGetAuctionById_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/collectioneer/auctions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.artwork.id").exists())
                .andExpect(jsonPath("$.currentBid").exists());
    }

    @Test
    public void getAllActiveExhibitions_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/exhibitions/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserById_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/users/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void getExistingArtwork_shouldReturn200() throws Exception {
        mockMvc.perform(get("/artworks/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void deleteNonExistingArtwork_shouldReturn400() throws Exception {
        mockMvc.perform(delete("/artworks/{id}", 1000))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void getNonExistingArtwork_shouldReturn400() throws Exception {
        mockMvc.perform(get("/artworks/{id}", 1000))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void testDisplayCurrentBidOnNonExistingAuction_shouldReturn500() throws Exception {
        mockMvc.perform(get("/auctions/{id}/currentBid", 100))
                .andExpect(status().is5xxServerError());
    }


    @Test
    public void testGetArtistCollection_shouldReturn500() throws Exception {
        mockMvc.perform(get("/users/{id}/collection", 1))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetNonExistingUserCollection_shouldReturn400() throws Exception {
        mockMvc.perform(get("/users/{id}/collection", 200))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "col", authorities = "CURATOR")
    public void testRemoveArtworkFromCollection_shouldReturn200() throws Exception {
        mockMvc.perform(put("/users/{id}/collection/remove", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "col", authorities = "CURATOR")
    public void testAddArtworkToCollection_shouldReturn200() throws Exception {
        mockMvc.perform(put("/users/{id}/collection/add", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3"))
                .andExpect(status().isOk());
    }

}
