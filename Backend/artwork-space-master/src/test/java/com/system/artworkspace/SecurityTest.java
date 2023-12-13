package com.system.artworkspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

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

    @WithMockUser(username = "art1", authorities = "ARTIST")
    @Test
    public void testGetRequestOnPrivateControllerWithoutRightRole_shouldReturn403() throws Exception {
        for(int i=0;i<5;i++){
            mockMvc.perform(get("/exhibitions/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful());
        }
    }

    @Test
    public void givenUnauthorizedUserOnGetRequestExhibition_shouldReturn200() throws Exception {
        mockMvc.perform(get("/exhibitions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void givenUnauthorizedUserOnGetRequestArtwork_shouldReturn200() throws Exception {
        mockMvc.perform(get("/artworks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(username = "art1", password = "password", authorities = "ARTIST")
    @Test
    public void givenAuthorizedUserOnGetRequestAuction_shouldReturn200() throws Exception {
        mockMvc.perform(get("/auctions/active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "art1", password = "password", authorities = "CURATOR")
    public void testCuratorCanAccessExhibitions_shouldReturn200() throws Exception {
        mockMvc.perform(get("/exhibitions/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ARTIST")
    public void testArtistCanAccessAuctions_shouldReturn200() throws Exception {
        mockMvc.perform(get("/auctions/active"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "COLLECTIONEER")
    public void testCollectioneerCanAccessUsers_shouldReturn200() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnauthorizedPostRequestToAuction_shouldReturn500() throws Exception {
        mockMvc.perform(post("/auctions"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "ARTIST")
    public void testPostExhibitionCreationWithoutRightRole_shouldReturn400() throws Exception {
        mockMvc.perform(post("/exhibitions"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "CURATOR")
    public void testPostAuctionCreationWithoutRightRole_shouldReturn400() throws Exception {
        mockMvc.perform(post("/auctions"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "COLLECTIONEER")
    public void testPostArtworkCreationWithoutRightRole_shouldReturn400() throws Exception {
        mockMvc.perform(post("/artworks"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostExhibitionCreationWithoutAuthentication_shouldReturn400() throws Exception {
        mockMvc.perform(post("/exhibitions"))
                .andExpect(status().is4xxClientError());
    }

}
