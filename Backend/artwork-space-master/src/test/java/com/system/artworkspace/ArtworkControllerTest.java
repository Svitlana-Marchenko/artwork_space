package com.system.artworkspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void deleteNonExistingArtwork() throws Exception {
        mockMvc.perform(delete("/artworks/{id}", 256))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void deleteExistingArtwork() throws Exception {
        mockMvc.perform(delete("/artworks/{id}", 2))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = "artist", authorities = "ARTIST")
    public void getExistingArtwork() throws Exception {
        mockMvc.perform(get("/artworks/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }
}
