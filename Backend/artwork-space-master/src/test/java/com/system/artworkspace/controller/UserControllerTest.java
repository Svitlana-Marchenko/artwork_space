package com.system.artworkspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.security.auth.jwt.JwtAuthenticationFilter;
import com.system.artworkspace.security.auth.jwt.JwtService;
import com.system.artworkspace.user.UserController;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.user.UserService;
import com.system.artworkspace.user.changePassword.ChangePasswordDTO;
import com.system.artworkspace.user.userUpdate.UserUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void testGetUserById_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUsersCollectionById_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}/collection", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateUser_shouldReturn200() throws Exception {
        UserDto userDto = new UserDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/new")
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateUser_shouldReturn200() throws Exception {
        UserUpdate userUpdate = new UserUpdate(1L, "username", "name", "lastname");
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePassword_shouldReturn200() throws Exception {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/password")
                        .with(SecurityMockMvcRequestPostProcessors.user("cur").roles("CURATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().isOk());
    }
}
