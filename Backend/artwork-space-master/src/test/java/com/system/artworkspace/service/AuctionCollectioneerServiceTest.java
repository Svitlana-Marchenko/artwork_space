package com.system.artworkspace.service;

import com.system.artworkspace.artwork.*;
import com.system.artworkspace.auction.*;
import com.system.artworkspace.exceptions.NoSuchAuctionException;
import com.system.artworkspace.user.*;
import com.system.artworkspace.utils.ArtworkUtils;
import com.system.artworkspace.utils.AuctionUtils;
import com.system.artworkspace.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuctionCollectioneerServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuctionCollectioneerServiceImpl auctionCollectioneerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPlaceBid_whenValidBidProvided() {
        Long auctionId = 1L;
        double bidAmount = 200.0;

        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        auctionEntity.setCurrentBid(150.0);
        auctionEntity.setBid(10.0);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user123");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = new User();
        user.setUsername("user123");
        when(userService.getUserByUsername("user123")).thenReturn(user);

        Auction placedAuction = auctionCollectioneerService.placeBid(auctionId, bidAmount);

        assertEquals(bidAmount, placedAuction.getCurrentBid());
        verify(auctionRepository, times(1)).save(any(AuctionEntity.class));
    }

    @Test
    public void shouldThrowException_whenBidAmountIsLessThanCurrentBidPlusMinimum() {
        Long auctionId = 1L;
        double bidAmount = 160.0;

        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        auctionEntity.setCurrentBid(150.0);
        auctionEntity.setBid(10.0);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));

        assertThrows(RuntimeException.class, () -> auctionCollectioneerService.placeBid(auctionId, bidAmount));
    }

    @Test
    public void shouldReturnCurrentBid_whenAuctionExists() {
        Long auctionId = 1L;
        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        auctionEntity.setCurrentBid(250.0);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auctionEntity));

        double currentBid = auctionCollectioneerService.getCurrentBid(auctionId);

        assertEquals(250.0, currentBid);
        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldThrowException_whenAuctionNotFoundForCurrentBid() {
        Long auctionId = 1L;
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());

        assertThrows(NoSuchAuctionException.class, () -> auctionCollectioneerService.getCurrentBid(auctionId));

        verify(auctionRepository, times(1)).findById(auctionId);
    }

    @Test
    public void shouldReturnArtwork_whenArtworkExistsInAuction() {
        Long artworkId = 1L;
        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(artworkId);

        when(artworkRepository.findById(artworkId)).thenReturn(Optional.of(artworkEntity));

        Artwork artwork = auctionCollectioneerService.getArtworkFromAuction(artworkId);

        assertNotNull(artwork);
        assertEquals(artworkId, artwork.getId());
        verify(artworkRepository, times(1)).findById(artworkId);
    }

    @Test
    public void shouldReturnNull_whenArtworkNotFoundInAuction() {
        Long artworkId = 1L;
        when(artworkRepository.findById(artworkId)).thenReturn(Optional.empty());

        Artwork artwork = auctionCollectioneerService.getArtworkFromAuction(artworkId);

        assertNull(artwork);
        verify(artworkRepository, times(1)).findById(artworkId);
    }

    @Test
    public void shouldReturnAuctionByPaintingId_whenPaintingExists() {
        Long paintingId = 1L;
        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(paintingId);

        AuctionEntity auctionEntity = AuctionUtils.getRandomAuctionEntity();
        auctionEntity.setId(paintingId);

        when(artworkRepository.findById(paintingId)).thenReturn(Optional.of(artworkEntity));
        when(auctionRepository.findById(paintingId)).thenReturn(Optional.of(auctionEntity));

        Auction auction = auctionCollectioneerService.getAuctionByPaintingId(paintingId);

        assertNotNull(auction);
        assertEquals(paintingId, auction.getId());
        verify(artworkRepository, times(1)).findById(paintingId);
        verify(auctionRepository, times(1)).findById(paintingId);
    }

    @Test
    public void shouldThrowException_whenAuctionNotFoundByPaintingId() {
        Long paintingId = 1L;
        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(paintingId);

        when(artworkRepository.findById(paintingId)).thenReturn(Optional.of(artworkEntity));
        when(auctionRepository.findById(paintingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> auctionCollectioneerService.getAuctionByPaintingId(paintingId));
    }

    @Test
    public void shouldReturnAllAuctionsByCustomerId_whenValidCustomerIdProvided() {
        Long customerId = 1L;
        User user = UserUtils.getRandomUser();
        user.setRole(Role.COLLECTIONEER);

        List<AuctionEntity> auctionEntities = new ArrayList<>();
        auctionEntities.add(AuctionUtils.getRandomAuctionEntity());

        when(userService.getUserById(customerId)).thenReturn(user);
        when(auctionRepository.findAllAuctionsByUserId(customerId)).thenReturn(auctionEntities);

        List<Auction> auctions = auctionCollectioneerService.getAllAuctionsByCustomerId(customerId);

        assertFalse(auctions.isEmpty());
        verify(auctionRepository, times(1)).findAllAuctionsByUserId(customerId);
    }

    @Test
    public void shouldThrowException_whenNonCollectioneerUserTriesToGetAuctions() {
        Long customerId = 1L;
        User user = UserUtils.getRandomUser();
        user.setRole(Role.ARTIST);

        when(userService.getUserById(customerId)).thenReturn(user);

        assertThrows(RuntimeException.class, () -> auctionCollectioneerService.getAllAuctionsByCustomerId(customerId));
    }
}

