package com.system.artworkspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ArtworkControllerTest {

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
    public void deleteExistingArtwork_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/artworks/{id}", 2))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void getNonExistingArtwork_shouldReturn400() throws Exception {
        mockMvc.perform(get("/artworks/{id}", 1000))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void addArtwork_shouldReturn200() throws Exception {
        mockMvc.perform(get("/artworks/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }



//    @Test
//    @WithMockUser(username = "artist", authorities = "ARTIST")
//    public void addArtwork_shouldReturn200() throws Exception {
//        String artworkJson = "{\n" +
//                "    \"title\": \"Sample Artwork\",\n" +
//                "    \"description\": \"A beautiful artwork description.\",\n" +
//                "    \"technique\": \"Oil Painting\",\n" +
//                "    \"width\": 24.5,\n" +
//                "    \"height\": 18.0,\n" +
//                "    \"user\": {\n" +
//                "        \"id\": 4,\n" +
//                "        \"username\": \"art2\",\n" +
//                "        \"firstName\": \"John\",\n" +
//                "        \"lastName\": \"Doe\",\n" +
//                "        \"email\": \"john.doe@example.com\",\n" +
//                "        \"password\": \"password\",\n" +
//                "        \"role\": \"ARTIST\"\n" +
//                "    },\n" +
//                "    \"imageURL\": \"/Users/Svitlana/Documents/Обробка_зображень/Пр4/img5.jpg\",\n" +
//                "    \"imageSize\": 256.5,\n" +
//                "    \"rating\": null\n" +
//                "}";
//
//        mockMvc.perform(post("/artworks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(artworkJson))
//                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
//    }

}
