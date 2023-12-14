package com.system.artworkspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.exhibition.ExhibitionController;
import com.system.artworkspace.exhibition.ExhibitionDto;
import com.system.artworkspace.exhibition.ExhibitionService;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;
import com.system.artworkspace.security.auth.jwt.JwtAuthenticationFilter;
import com.system.artworkspace.security.auth.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExhibitionController.class)
public class ExhibitionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExhibitionService exhibitionService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void testGetAllActiveExhibition_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exhibitions/active")
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetExhibitionById_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exhibitions/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllExhibitionsByCuratorId_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exhibitions/curator/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateExhibition_shouldReturn200() throws Exception {
        ExhibitionDto ExhibitionDto = new ExhibitionDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/exhibitions")
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ExhibitionDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateExhibition_shouldReturn200() throws Exception {
        ExhibitionUpdate exhibitionUpdate = new ExhibitionUpdate(1L, "TITLE", "DESC");
        mockMvc.perform(MockMvcRequestBuilders.put("/exhibitions")
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exhibitionUpdate)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExhibition_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/exhibitions/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }
}

