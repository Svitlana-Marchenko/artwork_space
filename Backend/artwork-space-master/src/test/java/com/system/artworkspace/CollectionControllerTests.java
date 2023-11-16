package com.system.artworkspace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.collection.CollectionController;
import com.system.artworkspace.collection.CollectionDto;
import com.system.artworkspace.collection.CollectionService;
import com.system.artworkspace.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CollectionController.class)
public class CollectionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CollectionService collectionService;
    @Autowired
    private WebApplicationContext context;

    @Test
    @WithMockUser(username = "col", roles = "COLLECTIONEER")
    public void testAddToCollection() throws Exception {
        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setDescription("Desc");
        artworkDto.setHeight(6);
        artworkDto.setTechnique("Tech");
        artworkDto.setTitle("Title");
        artworkDto.setId(99L);
        artworkDto.setUser(new UserDto());
        artworkDto.setWidth(8);
        artworkDto.setImageURL("url");
        artworkDto.setImageSize(8);

        mockMvc.perform(MockMvcRequestBuilders.post("/collections/{id}/addArtwork", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(artworkDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "col", authorities = "COLLECTIONEER")
    public void testDeleteCollection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/collections/{id}", 1))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "col", authorities = "COLLECTIONEER")
    public void testEditName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/collections/{id}/editName", 1)
                .param("name", "New Collection Name"))
                .andExpect(status().isOk());
    }
}
