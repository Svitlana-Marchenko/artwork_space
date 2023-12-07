package com.system.artworkspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ExhibitionControllerTest {

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
    public void getExhibitionById() throws Exception {
        // Implement your test logic for getting an exhibition by ID
        // You may need to mock the ExhibitionService and define its behavior

        mockMvc.perform(get("/exhibitions/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void getExhibitionByCuratorId() throws Exception {
        // Implement your test logic for getting exhibitions by curator ID
        // You may need to mock the ExhibitionService and define its behavior

        mockMvc.perform(get("/exhibitions/curator/{curatorId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].description").exists());
    }

    @Test
    public void getAllActiveExhibitions() throws Exception {
        // Implement your test logic for getting all active exhibitions
        // You may need to mock the ExhibitionService and define its behavior

        mockMvc.perform(get("/exhibitions/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].description").exists());
    }

    @Test
    public void deleteExhibition() throws Exception {
        // Implement your test logic for deleting an exhibition
        // You may need to mock the ExhibitionService and define its behavior

        mockMvc.perform(delete("/exhibitions/{id}", 1))
                .andExpect(status().isOk());
    }

}

