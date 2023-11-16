package com.system.artworkspace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.collection.CollectionController;
import com.system.artworkspace.collection.CollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CollectionController.class)
public class test {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CollectionService collectionService;
    @Test
    @WithMockUser(username = "col", authorities = "COLLECTIONEER")
    public void testDeleteCollection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/collections/{id}", 1))
                .andExpect(status().isOk());
    }
}
