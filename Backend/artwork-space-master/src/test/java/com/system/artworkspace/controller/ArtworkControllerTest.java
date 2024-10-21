package com.system.artworkspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.artwork.*;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateDto;
import com.system.artworkspace.rating.RatingDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtworkController.class)
public class ArtworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArtworkService artworkService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void testGetAllArtworks_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/artworks")
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteArtwork_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/artworks/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllArtworksByUserId_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/artworks/artist/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetArtworkById_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/artworks/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateArtwork_shouldReturn200() throws Exception {
        ArtworkDto artworkDto = new ArtworkDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/artworks")
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artworkDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateArtwork_shouldReturn200() throws Exception {
        ArtworkUpdateDto artworkDto = new ArtworkUpdateDto("title", "desc", "qwerty", 100, 100,  null);
        mockMvc.perform(MockMvcRequestBuilders.put("/artworks", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artworkDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddRating_shouldReturn200() throws Exception {
        RatingDto ratingDto = new RatingDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/artworks/1/ratings", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ratingDto)))
                .andExpect(status().isOk());
    }

}
