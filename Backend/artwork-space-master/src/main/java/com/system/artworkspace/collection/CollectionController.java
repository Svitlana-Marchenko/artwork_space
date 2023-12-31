package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.ValidationException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/collections")
@PreAuthorize("hasAuthority('COLLECTIONEER') || hasAuthority('CURATOR')")
public class CollectionController {
    private static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<CollectionDto> getAll(){
        logger.info("Getting all collections");
        return collectionService.getAllCollections().stream().map(x-> CollectionMapper.INSTANCE.collectionToCollectionDto(x)).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public CollectionDto getCollectionById(@PathVariable Long id){
        logger.info("Getting collection by id");
        return CollectionMapper.INSTANCE.collectionToCollectionDto(collectionService.getCollectionById(id));
    }
    @GetMapping("/{id}/artworks")
    public List<ArtworkDto> getArtworksByCollectionId(@PathVariable Long id){
        logger.info("Getting artworks by collection id");
        return collectionService.getArtworksByCollection(id).stream().map(x-> ArtworkMapper.INSTANCE.artworkToArtworkDto(x)).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<CollectionDto> getCollectionsByUserId(@PathVariable Long userId){
        logger.info("Getting collections by user id");
        return collectionService.getCollectionsByUserId(userId).stream().map(x-> CollectionMapper.INSTANCE.collectionToCollectionDto(x)).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}/all")
    public List<ArtworkDto> getArtworksFromCollectionsByUserId(@PathVariable Long userId){
        logger.info("Getting collections by user id");
        return collectionService.getArtworksFromCollectionsByUserId(userId).stream().map(x-> ArtworkMapper.INSTANCE.artworkToArtworkDto(x)).collect(Collectors.toList());
    }

    @PostMapping
    public CollectionDto createCollection(@RequestBody @Valid CollectionDto collection, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Creating a collection with ID: {}", collection.getId());
        CollectionDto createdCollection = CollectionMapper.INSTANCE.collectionToCollectionDto(collectionService.createCollection(CollectionMapper.INSTANCE.collectionDtoToCollection(collection)));
        logger.info("Collection created with ID: {}", createdCollection.getId());
        return createdCollection;
    }

    @PostMapping("/{collectionId}/addArtwork")
    public void addToCollection(@PathVariable Long collectionId, @RequestParam Long artworkId) {
        logger.info("Adding artwork with ID {} to collection with ID: {}",artworkId, collectionId);
        collectionService.addToCollection(collectionId, artworkId);
        logger.info("Artwork added to collection with ID: {}", collectionId);
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable Long collectionId) {
        logger.info("Deleting collection with ID: {}", collectionId);
        collectionService.deleteCollection(collectionId);
        logger.info("Collection deleted with ID: {}", collectionId);
    }

    @DeleteMapping("/{collectionId}/removeArtwork")
    public void deleteFromCollection(@PathVariable Long collectionId, @RequestParam Long artworkId) {
        logger.info("Removing artwork with ID {} from collection with ID: {}", artworkId, collectionId);
        collectionService.deleteFromCollection(collectionId, artworkId);
        logger.info("Artwork removed from collection with ID: {}", collectionId);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/{collectionId}/editName")
    public void editName(@PathVariable Long collectionId, @RequestParam String name) {
        logger.info("Updating collection name for collection with ID: {}", collectionId);
        collectionService.editName(collectionId, name);
        logger.info("Collection name updated for collection with ID: {}", collectionId);
    }
}