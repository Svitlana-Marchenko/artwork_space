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
public class CollectionControllerTest {

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
    public void testGetAllCollections() throws Exception {
       mockMvc.perform(get("/collections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void testCreateCollection() throws Exception {
        // Implement your test logic for creating a collection
        // You may need to mock the CollectionService and define its behavior

        mockMvc.perform(post("/collections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Test Collection\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testAddArtworkToCollection() throws Exception {
        // Implement your test logic for adding artwork to a collection
        // You may need to mock the CollectionService and define its behavior

        mockMvc.perform(post("/collections/{collectionId}/addArtwork", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"title\": \"Artwork Title\", \"artist\": \"Artist Name\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCollection() throws Exception {
        // Implement your test logic for deleting a collection
        // You may need to mock the CollectionService and define its behavior

        mockMvc.perform(delete("/collections/{collectionId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFromCollection() throws Exception {
        // Implement your test logic for removing artwork from a collection
        // You may need to mock the CollectionService and define its behavior

        mockMvc.perform(delete("/collections/{collectionId}/removeArtwork", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"title\": \"Artwork Title\", \"artist\": \"Artist Name\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditCollectionName() throws Exception {
        // Implement your test logic for editing the name of a collection
        // You may need to mock the CollectionService and define its behavior

        mockMvc.perform(put("/collections/{collectionId}/editName", 1)
                        .param("name", "Updated Collection Name"))
                .andExpect(status().isOk());
    }

    // Add more tests for other methods in the CollectionController

}

