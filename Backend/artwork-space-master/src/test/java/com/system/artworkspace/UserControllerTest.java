package com.system.artworkspace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    public void testCreateUser() throws Exception {
        // Implement your test logic for creating a user
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testuser\", \"password\": \"testpassword\", \"email\": \"test@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Implement your test logic for updating a user
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"username\": \"updateduser\", \"email\": \"updated@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Implement your test logic for deleting a user
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
        // Implement your test logic for getting a user by ID
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(get("/users/{userId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    public void testChangePassword() throws Exception {
        // Implement your test logic for changing a user's password
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"oldPassword\": \"oldpass\", \"newPassword\": \"newpass\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCollectionByUserId() throws Exception {
        // Implement your test logic for getting a user's collection
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(get("/users/{id}/collection", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].artist").exists());
    }

    @Test
    public void testRemoveArtworkFromCollection() throws Exception {
        // Implement your test logic for removing artwork from a user's collection
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(put("/users/{id}/collection/remove", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddArtworkToCollection() throws Exception {
        // Implement your test logic for adding artwork to a user's collection
        // You may need to mock the UserService and define its behavior

        mockMvc.perform(put("/users/{id}/collection/add", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3"))
                .andExpect(status().isOk());
    }

}
