package com.system.artworkspace.service;

import com.system.artworkspace.auction.*;
import com.system.artworkspace.auction.Sale.Sale;
import com.system.artworkspace.auction.Sale.SaleService;
import com.system.artworkspace.exceptions.NoSuchAuctionException;
import com.system.artworkspace.user.*;
import com.system.artworkspace.utils.AuctionUtils;
import com.system.artworkspace.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuctionArtistServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private UserService userService;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private AuctionArtistServiceImpl auctionArtistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAuction_whenAuctionExistsById() {
        Long auctionId = 1L;
        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        auctionEntity.setId(auctionId);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));

        Auction auction = auctionArtistService.getAuctionById(auctionId);

        assertNotNull(auction);
        assertEquals(auctionId, auction.getId());
        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldThrowException_whenAuctionNotFoundById() {
        Long auctionId = 1L;
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> auctionArtistService.getAuctionById(auctionId));

        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldReturnCurrentBid_whenAuctionExists() {
        Long auctionId = 1L;
        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        auctionEntity.setCurrentBid(150.0);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));

        double currentBid = auctionArtistService.displayCurrentBid(auctionId);

        assertEquals(150.0, currentBid);
        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldThrowException_whenAuctionNotFoundForCurrentBid() {
        Long auctionId = 1L;
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(NoSuchAuctionException.class, () -> auctionArtistService.displayCurrentBid(auctionId));

        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldReturnCurrentBuyer_whenAuctionExists() {
        Long auctionId = 1L;
        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        UserEntity userEntity = UserUtils.getRandomUserEntity();
        auctionEntity.setUser(userEntity);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));

        User buyer = auctionArtistService.displayCurrentBuyer(auctionId);

        assertNotNull(buyer);
        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldReturnNull_whenAuctionNotFoundForBuyer() {
        Long auctionId = 1L;
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        User buyer = auctionArtistService.displayCurrentBuyer(auctionId);

        assertNull(buyer);
        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldCloseAuction_whenValidAuctionIdProvided() {
        Long auctionId = 1L;
        Auction auction = AuctionUtils.getRandomAuction();
        auction.setId(auctionId);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(AuctionUtils.getRandomAuctionEntity()));

        auctionArtistService.closeAuction(auctionId);

        verify(saleService, times(1)).createSale(any(Sale.class));
        verify(auctionRepository, times(1)).deleteById(auctionId);
    }

    @Test
    public void shouldDeleteAuction_whenValidAuctionIdProvided() {
        Long auctionId = 1L;
        doNothing().when(auctionRepository).deleteById(auctionId);

        auctionArtistService.deleteAuctionById(auctionId);

        verify(auctionRepository, times(1)).deleteById(auctionId);
    }

    @Test
    public void shouldReturnAuctionsByUserId_whenValidUserIdProvided() {
        Long userId = 1L;
        User user = UserUtils.getRandomUser();
        user.setRole(Role.ARTIST);

        List<AuctionEntity> auctionEntities = new ArrayList<>();
        auctionEntities.add(AuctionUtils.getRandomAuctionEntity());

        when(userService.getUserById(userId)).thenReturn(user);
        when(auctionRepository.findAllAuctionsByArtworkArtistId(userId)).thenReturn(auctionEntities);

        List<Auction> auctions = auctionArtistService.getAllAuctionsByUserId(userId);

        assertFalse(auctions.isEmpty());
        verify(auctionRepository, times(1)).findAllAuctionsByArtworkArtistId(userId);
    }
}
