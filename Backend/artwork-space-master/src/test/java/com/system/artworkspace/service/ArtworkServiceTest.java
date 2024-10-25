package com.system.artworkspace.service;

import com.system.artworkspace.artwork.*;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdate;
import com.system.artworkspace.auction.Sale.SaleRepository;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.utils.ArtworkUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArtworkServiceTest {

    @Mock
    private ArtworkRepository repository;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ArtworkServiceImpl artworkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllArtwork_whenArtworkExists() {
        List<ArtworkEntity> artworkEntities = new ArrayList<>();
        artworkEntities.add(ArtworkUtils.getRandomArtworkEntity());
        when(repository.findAll()).thenReturn(artworkEntities);

        List<Artwork> artworks = artworkService.getAllArtwork();

        assertEquals(1, artworks.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldAddArtworkWithFile_whenValidArtworkAndFileProvided() throws IOException {
        Artwork artwork = ArtworkUtils.getRandomArtwork();
        artwork.setId(1L);

        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(1L);

        when(repository.save(any(ArtworkEntity.class))).thenReturn(artworkEntity);
        when(file.getOriginalFilename()).thenReturn("image.jpg");

        Artwork savedArtwork = artworkService.addArtwork(artwork, file);

        assertEquals(artwork.getId(), savedArtwork.getId());
        verify(repository, times(1)).save(any(ArtworkEntity.class));
    }

    @Test
    public void shouldThrowException_whenArtworkToDeleteDoesNotExist() {
        Long artworkId = 1L;
        when(repository.findById(artworkId)).thenReturn(Optional.empty());

        assertThrows(NoSuchArtworkException.class, () -> artworkService.deleteArtwork(artworkId));

        verify(repository, times(1)).findById(artworkId);
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    public void shouldUpdateArtwork_whenArtworkExists() {
        Long artworkId = 1L;
        ArtworkUpdate artworkUpdate = ArtworkUtils.getRandomArtworkUpdate();

        ArtworkEntity artworkEntity = new ArtworkEntity();
        artworkEntity.setId(artworkId);

        when(repository.findById(artworkId)).thenReturn(Optional.of(artworkEntity));

        Artwork updatedArtwork = artworkService.updateArtwork(artworkId, artworkUpdate);

        assertEquals(artworkUpdate.getTitle(), updatedArtwork.getTitle());
        verify(repository, times(1)).save(any(ArtworkEntity.class));
    }

    @Test
    public void shouldReturnArtworkById_whenArtworkExists() {
        Long artworkId = 1L;
        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(artworkId);

        when(repository.findById(artworkId)).thenReturn(Optional.of(artworkEntity));

        Artwork artwork = artworkService.getArtworkById(artworkId);

        assertNotNull(artwork);
        assertEquals(artworkId, artwork.getId());
        verify(repository, times(1)).findById(artworkId);
    }

    @Test
    public void shouldThrowException_whenArtworkNotFoundById() {
        Long artworkId = 1L;
        when(repository.findById(artworkId)).thenReturn(Optional.empty());

        assertThrows(NoSuchArtworkException.class, () -> artworkService.getArtworkById(artworkId));

        verify(repository, times(1)).findById(artworkId);
    }

    @Test
    public void shouldAddRating_whenArtworkExists() {
        Long artworkId = 1L;
        Rating rating = ArtworkUtils.getRandomRating();
        rating.setId(1L);

        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(artworkId);
        when(repository.findById(artworkId)).thenReturn(Optional.of(artworkEntity));

        artworkService.addRating(artworkId, rating);

        verify(repository, times(1)).save(artworkEntity);
    }

    @Test
    public void shouldDeleteRating_whenArtworkExists() {
        Long artworkId = 1L;
        Rating rating = ArtworkUtils.getRandomRating();
        rating.setId(1L);

        ArtworkEntity artworkEntity = ArtworkUtils.getRandomArtworkEntity();
        artworkEntity.setId(artworkId);

        when(repository.findById(artworkId)).thenReturn(Optional.of(artworkEntity));

        artworkService.deleteRating(artworkId, rating);

        verify(repository, times(1)).save(artworkEntity);
    }

    @Test
    public void shouldReturnTrue_whenArtworkIsSold() {
        Long artworkId = 1L;
        when(saleRepository.existsSaleEntityByArtworkId(artworkId)).thenReturn(true);

        assertTrue(artworkService.isSold(artworkId));
        verify(saleRepository, times(1)).existsSaleEntityByArtworkId(artworkId);
    }

    @Test
    public void shouldReturnFalse_whenArtworkIsNotSold() {
        Long artworkId = 1L;
        when(saleRepository.existsSaleEntityByArtworkId(artworkId)).thenReturn(false);

        assertFalse(artworkService.isSold(artworkId));
        verify(saleRepository, times(1)).existsSaleEntityByArtworkId(artworkId);
    }
}

