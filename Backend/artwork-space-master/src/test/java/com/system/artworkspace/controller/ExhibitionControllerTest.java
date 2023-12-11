package com.system.artworkspace.controller;

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
        mockMvc.perform(get("/exhibitions/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getExhibitionByCuratorId() throws Exception {
         mockMvc.perform(get("/exhibitions/curator/{curatorId}", 1))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getAllActiveExhibitions() throws Exception {
        mockMvc.perform(get("/exhibitions/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void deleteExhibition() throws Exception {
        mockMvc.perform(delete("/exhibitions/{id}", 1))
                .andExpect(status().isOk());
    }

}

