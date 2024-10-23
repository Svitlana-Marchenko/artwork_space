package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.ValidationException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CollectionController {

    private final CollectionService collectionService;

    @Autowired
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<CollectionDto> getAll(){
        log.info("Getting all collections");
        return collectionService.getAllCollections().stream().map(CollectionMapper.INSTANCE::collectionToCollectionDto).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public CollectionDto getCollectionById(@PathVariable Long id){
        log.debug("Getting collection by id");
        return CollectionMapper.INSTANCE.collectionToCollectionDto(collectionService.getCollectionById(id));
    }
    @GetMapping("/{id}/artworks")
    public List<ArtworkDto> getArtworksByCollectionId(@PathVariable Long id){
        log.debug("Getting artworks by collection id");
        return collectionService.getArtworksByCollection(id).stream().map(ArtworkMapper.INSTANCE::artworkToArtworkDto).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<CollectionDto> getCollectionsByUserId(@PathVariable Long userId){
        log.debug("Getting collections by user id");
        return collectionService.getCollectionsByUserId(userId).stream().map(CollectionMapper.INSTANCE::collectionToCollectionDto).collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}/all")
    public List<ArtworkDto> getArtworksFromCollectionsByUserId(@PathVariable Long userId){
        log.debug("Getting collections by user id");
        return collectionService.getArtworksFromCollectionsByUserId(userId).stream().map(ArtworkMapper.INSTANCE::artworkToArtworkDto).collect(Collectors.toList());
    }

    @PostMapping
    public CollectionDto createCollection(@RequestBody @Valid CollectionDto collection, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        CollectionDto createdCollection = CollectionMapper.INSTANCE.collectionToCollectionDto(collectionService.createCollection(CollectionMapper.INSTANCE.collectionDtoToCollection(collection)));
        log.info("Collection created with ID: {}", createdCollection.getId());
        return createdCollection;
    }

    @PostMapping("/{collectionId}/addArtwork")
    public void addToCollection(@PathVariable Long collectionId, @RequestParam Long artworkId) {
        log.info("Adding artwork with ID {} to collection with ID: {}",artworkId, collectionId);
        collectionService.addToCollection(collectionId, artworkId);
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable Long collectionId) {
        log.info("Deleting collection with ID: {}", collectionId);
        collectionService.deleteCollection(collectionId);
    }

    @DeleteMapping("/{collectionId}/removeArtwork")
    public void deleteFromCollection(@PathVariable Long collectionId, @RequestParam Long artworkId) {
        log.info("Removing artwork with ID {} from collection with ID: {}", artworkId, collectionId);
        collectionService.deleteFromCollection(collectionId, artworkId);
    }

    @PutMapping("/{collectionId}/editName")
    public void editName(@PathVariable Long collectionId, @RequestParam String name) {
        log.info("Updating collection name for collection with ID: {}", collectionId);
        collectionService.editName(collectionId, name);
        log.info("Collection name updated for collection with ID: {}", collectionId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}