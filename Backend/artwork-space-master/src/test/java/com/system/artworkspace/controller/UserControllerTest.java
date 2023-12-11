package com.system.artworkspace.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

//    @Test
//    public void testCreateUser() throws Exception {
//        // Implement your test logic for creating a user
//        // You may need to mock the UserService and define its behavior
//
//        mockMvc.perform(post("/users/new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"username\": \"testuser\", \"password\": \"testpassword\", \"email\": \"test@example.com\" }"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists());
//    }

//    @Test
//    public void testUpdateUser() throws Exception {
//        mockMvc.perform(put("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"id\": 1, \"username\": \"updateduser\", \"email\": \"updated@example.com\" }"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.username").value("updateduser"))
//                .andExpect(jsonPath("$.email").value("updated@example.com"));
//    }

//    @Test
//    @WithMockUser(username = "artist", authorities = "ARTIST")
//    public void testDeleteUser() throws Exception {
//        mockMvc.perform(delete("/users/{id}", 2))
//                .andExpect(status().isOk());
//    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void testChangePassword_shouldReturn200() throws Exception {
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"oldPassword\": \"password\", \"newPassword\": \"Newpass12345\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePasswordWithIncorrectPassword_shouldReturn400() throws Exception {
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"oldPassword\": \"password1\", \"newPassword\": \"Newpass12345\" }"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testChangePasswordWithIncorrectNewPassword_shouldReturn500() throws Exception {
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"oldPassword\": \"password\", \"newPassword\": \"12345678\" }"))
                .andExpect(status().is5xxServerError()  );
    }

    @Test
    public void testGetArtistCollection_shouldReturn500() throws Exception {
         mockMvc.perform(get("/users/{id}/collection", 1))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetUserCollection_shouldReturn200() throws Exception {
        mockMvc.perform(get("/users/{id}/collection", 2))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNonExistingUserCollection_shouldReturn400() throws Exception {
        mockMvc.perform(get("/users/{id}/collection", 200))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testRemoveArtworkFromCollection_shouldReturn200() throws Exception {
        mockMvc.perform(put("/users/{id}/collection/remove", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddArtworkToCollection_shouldReturn200() throws Exception {
         mockMvc.perform(put("/users/{id}/collection/add", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddArtworkToArtistCollection_shouldReturn500() throws Exception {
        mockMvc.perform(put("/users/{id}/collection/add", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testRemoveArtworkToArtistCollection_shouldReturn500() throws Exception {
        mockMvc.perform(put("/users/{id}/collection/add", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3"))
                .andExpect(status().is5xxServerError());
    }
}
