package com.system.artworkspace.service;

import com.system.artworkspace.artwork.*;
import com.system.artworkspace.collection.*;
import com.system.artworkspace.utils.ArtworkUtils;
import com.system.artworkspace.utils.CollectionUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CollectionServiceTest {

    @Mock
    private CollectionRepository repository;

    @Mock
    private ArtworkService artworkService;

    @InjectMocks
    private CollectionServiceImpl collectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateCollection_whenValidCollectionProvided() {
        Collection collection = CollectionUtils.getRandomCollection();
        collection.setId(1L);

        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntity.setId(1L);

        when(repository.save(any(CollectionEntity.class))).thenReturn(collectionEntity);

        Collection createdCollection = collectionService.createCollection(collection);

        assertNotNull(createdCollection);
        assertEquals(collection.getId(), createdCollection.getId());
        verify(repository, times(1)).save(any(CollectionEntity.class));
    }

    @Test
    public void shouldReturnCollectionById_whenCollectionExists() {
        Long collectionId = 1L;
        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntity.setId(collectionId);

        when(repository.findById(collectionId)).thenReturn(Optional.of(collectionEntity));

        Collection collection = collectionService.getCollectionById(collectionId);

        assertNotNull(collection);
        assertEquals(collectionId, collection.getId());
        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldThrowException_whenCollectionNotFoundById() {
        Long collectionId = 1L;
        when(repository.findById(collectionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> collectionService.getCollectionById(collectionId));

        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldReturnCollectionsByUserId_whenCollectionsExistForUser() {
        Long userId = 1L;
        List<CollectionEntity> collectionEntities = new ArrayList<>();
        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntities.add(collectionEntity);

        when(repository.getCollectionEntitiesByOwnerId(userId)).thenReturn(collectionEntities);

        List<Collection> collections = collectionService.getCollectionsByUserId(userId);

        assertFalse(collections.isEmpty());
        verify(repository, times(1)).getCollectionEntitiesByOwnerId(userId);
    }

    @Test
    public void shouldAddToCollection_whenValidArtworkAndCollectionProvided() {
        Long collectionId = 1L;
        Long artworkId = 2L;

        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntity.setId(collectionId);

        Artwork artwork = ArtworkUtils.getRandomArtwork();
        artwork.setId(artworkId);

        when(repository.findById(collectionId)).thenReturn(Optional.of(collectionEntity));
        when(artworkService.getArtworkById(artworkId)).thenReturn(artwork);

        collectionService.addToCollection(collectionId, artworkId);

        verify(repository, times(1)).save(any(CollectionEntity.class));
        verify(artworkService, times(1)).getArtworkById(artworkId);
    }

    @Test
    public void shouldThrowException_whenCollectionNotFoundForAddToCollection() {
        Long collectionId = 1L;
        Long artworkId = 2L;

        when(repository.findById(collectionId)).thenReturn(Optional.empty());
        when(artworkService.getArtworkById(artworkId)).thenReturn(ArtworkUtils.getRandomArtwork());

        assertThrows(EntityNotFoundException.class, () -> collectionService.addToCollection(collectionId, artworkId));

        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldDeleteCollection_whenValidCollectionIdProvided() {
        Long collectionId = 1L;
        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntity.setId(collectionId);

        when(repository.findById(collectionId)).thenReturn(Optional.of(collectionEntity));

        collectionService.deleteCollection(collectionId);

        verify(repository, times(1)).delete(collectionEntity);
        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldThrowException_whenCollectionNotFoundForDelete() {
        Long collectionId = 1L;

        when(repository.findById(collectionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> collectionService.deleteCollection(collectionId));

        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldDeleteFromCollection_whenValidArtworkAndCollectionProvided() {
        Long collectionId = 1L;
        Long artworkId = 2L;

        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntity.setId(collectionId);

        Artwork artwork = new Artwork();
        artwork.setId(artworkId);

        when(repository.findById(collectionId)).thenReturn(Optional.of(collectionEntity));
        when(artworkService.getArtworkById(artworkId)).thenReturn(artwork);

        collectionService.deleteFromCollection(collectionId, artworkId);

        verify(repository, times(1)).save(any(CollectionEntity.class));
        verify(artworkService, times(1)).getArtworkById(artworkId);
    }

    @Test
    public void shouldThrowException_whenCollectionNotFoundForDeleteFromCollection() {
        Long collectionId = 1L;
        Long artworkId = 2L;

        when(repository.findById(collectionId)).thenReturn(Optional.empty());
        when(artworkService.getArtworkById(artworkId)).thenReturn(ArtworkUtils.getRandomArtwork());

        assertThrows(EntityNotFoundException.class, () -> collectionService.deleteFromCollection(collectionId, artworkId));

        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldEditCollectionName_whenValidCollectionIdProvided() {
        Long collectionId = 1L;
        String newName = "New Collection Name";

        CollectionEntity collectionEntity = CollectionUtils.getRandomCollectionEntity();
        collectionEntity.setId(collectionId);

        when(repository.findById(collectionId)).thenReturn(Optional.of(collectionEntity));

        collectionService.editName(collectionId, newName);

        assertEquals(newName, collectionEntity.getTitle());
        verify(repository, times(1)).save(collectionEntity);
        verify(repository, times(1)).findById(collectionId);
    }

    @Test
    public void shouldThrowException_whenCollectionNotFoundForEditName() {
        Long collectionId = 1L;
        String newName = "New Collection Name";

        when(repository.findById(collectionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> collectionService.editName(collectionId, newName));

        verify(repository, times(1)).findById(collectionId);
    }
}

