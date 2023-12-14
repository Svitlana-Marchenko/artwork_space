package com.system.artworkspace.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.auction.AuctionArtistController;
import com.system.artworkspace.auction.AuctionArtistService;
import com.system.artworkspace.auction.AuctionDto;
import com.system.artworkspace.security.auth.jwt.JwtAuthenticationFilter;
import com.system.artworkspace.security.auth.jwt.JwtService;
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
@WebMvcTest(AuctionArtistController.class)
public class AuctionArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuctionArtistService auctionArtistService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    public void testGetAllActiveAuctions_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auctions/active")
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllAuctionsByArtistId_ShouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auctions/artist/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateAuction_shouldReturn200() throws Exception {
        AuctionDto auctionDto = new AuctionDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/auctions")
                        .with(SecurityMockMvcRequestPostProcessors.user("art").roles("ARTIST"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(auctionDto)))
                .andExpect(status().isOk());
    }

}
