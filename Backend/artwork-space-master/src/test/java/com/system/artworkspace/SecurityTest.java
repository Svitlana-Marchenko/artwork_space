package com.system.artworkspace;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }




//    @Test
//    public void testAdminAccess() throws Exception {
//        mockMvc.perform(get("/admin")
//                        .with(user("admin").roles("ADMIN")))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testUserAccess() throws Exception {
//        mockMvc.perform(get("/user")
//                        .with(user("user").roles("USER")))
//                .andExpect(status().isOk());
//    }
//

    @WithMockUser(username = "art1", password = "password", authorities = "ARTIST")
    @Test
    public void givenPostRequestOnPrivateController_shouldReturn403() throws Exception {
        mockMvc.perform(post("/exhibitions/1/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "art1", password = "password", authorities = "ARTIST")
    @Test
    public void givenGetRequestOnPrivateController_shouldReturn200() throws Exception {
        mockMvc.perform(get("/exhibitions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    public void givenUnauthorizedUserOnPrivateController_expectIsUnauthorized() throws Exception {
        mockMvc.perform(get("/artworks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenUnauthorizedUserOnPrivateController_expect400Error() throws Exception {
        mockMvc.perform(get("/artworks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "art1", password = "password", authorities = "ARTIST")
    @Test
    public void givenGetRequestOnPrivateControllerWithRightRole_shouldReturn200() throws Exception {
        mockMvc.perform(get("/auctions/active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}