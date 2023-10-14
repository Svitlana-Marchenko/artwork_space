package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collections")
public class CollectionController {
    private static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping
    public CollectionDto createCollection(@RequestBody CollectionDto collection) {
        logger.info("Creating a collection with ID: {}", collection.getId());
        CollectionDto createdCollection = collectionService.createCollection(collection);
        logger.info("Collection created with ID: {}", createdCollection.getId());
        return createdCollection;
    }

    @PostMapping("/{collectionId}/addArtwork")
    public void addToCollection(@PathVariable Long collectionId, @RequestBody ArtworkDto artwork) {
        logger.info("Adding artwork with ID {} to collection with ID: {}", artwork.getId(), collectionId);
        //collectionService.addToCollection(collectionId, artwork);
        logger.info("Artwork added to collection with ID: {}", collectionId);
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable Long collectionId) {
        logger.info("Deleting collection with ID: {}", collectionId);
        //collectionService.deleteCollection(collectionId);
        logger.info("Collection deleted with ID: {}", collectionId);
    }

    @DeleteMapping("/{collectionId}/removeArtwork")
    public void deleteFromCollection(@PathVariable Long collectionId, @RequestBody ArtworkDto artwork) {
        logger.info("Removing artwork with ID {} from collection with ID: {}", artwork.getId(), collectionId);
        //collectionService.deleteFromCollection(collectionId, artwork);
        logger.info("Artwork removed from collection with ID: {}", collectionId);
    }

    @PutMapping("/{collectionId}/editName")
    public void editName(@PathVariable Long collectionId, @RequestParam String name) {
        logger.info("Updating collection name for collection with ID: {}", collectionId);
        //collectionService.editName(collectionId, name);
        logger.info("Collection name updated for collection with ID: {}", collectionId);
    }
}