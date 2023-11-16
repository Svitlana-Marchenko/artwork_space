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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollectionController.class)
public class CollectionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CollectionService collectionService;

    @Test
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
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(artworkDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCollection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/collections/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }


    @Test
    public void testEditName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/collections/{id}/editName", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("col").roles("COLLECTIONEER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", "New Collection Name"))
                .andExpect(status().isOk());
    }
}
