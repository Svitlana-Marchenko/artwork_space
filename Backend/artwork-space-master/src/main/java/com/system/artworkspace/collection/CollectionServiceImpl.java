package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.COLLECTION_EVENTS;

@Service
@Slf4j
public class CollectionServiceImpl implements CollectionService{

    private final CollectionRepository repository;

    private final ArtworkService artworkService;

    @Autowired
    public CollectionServiceImpl(CollectionRepository repository, ArtworkService artworkService) {
        this.repository = repository;
        this.artworkService = artworkService;
    }

    @Override
    public Collection createCollection(Collection collection) {
        repository.save(CollectionMapper.INSTANCE.collectionToCollectionEntity(collection));
        log.info(COLLECTION_EVENTS,"Created collection with ID: {}", collection.getId());
        return collection;
    }

    @Override
    public Collection getCollectionById(Long id) {
        Optional<CollectionEntity> collection = repository.findById(id);
        log.debug("Finding collection by id (without CACHE): {}", id);
        if (collection.isPresent())
            return CollectionMapper.INSTANCE.collectionEntityToCollection(collection.get());
        else
            throw new EntityNotFoundException("Collection with id " + id + " not found");

    }

    @Override
    public List<Collection> getCollectionsByUserId(Long id) {
        return repository.getCollectionEntitiesByOwnerId(id).stream().map(CollectionMapper.INSTANCE::collectionEntityToCollection).collect(Collectors.toList());
    }

    @Override
    public List<Artwork> getArtworksFromCollectionsByUserId(Long id) {
        List<Collection> collections = getCollectionsByUserId(id);
        List<List<Artwork>> listOfLists = new LinkedList<>();
        for(Collection col: collections){
            listOfLists.add(col.getArtworks());
        }
        return unionArtworkLists(listOfLists);
    }

    @Override
    public List<Artwork> getArtworksByCollection(Long id) {
        return getCollectionById(id).getArtworks();
    }

    private List<Artwork> unionArtworkLists(List<List<Artwork>> listOfLists) {
        return listOfLists.stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void addToCollection(Long id,  Long artworkId) {
        Optional<CollectionEntity> collectionEntity = repository.findById(id);
        Artwork artwork = artworkService.findArtworkById(artworkId);

         if (collectionEntity.isPresent()) {
            Optional<CollectionEntity> optionalCollection = repository.findById(id);

            if (optionalCollection.isPresent()) {
                CollectionEntity existingCollectionEntity = optionalCollection.get();
                existingCollectionEntity.addNewArtwork(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
                repository.save(existingCollectionEntity);
                log.info(COLLECTION_EVENTS,"Added artwork with ID {} to collection with ID: {}", artwork.getId(), id);
            } else {
                log.warn(COLLECTION_EVENTS,"Collection not found for adding artwork with ID: {} to collection with ID: {}", artwork.getId(), id);
                throw new EntityNotFoundException("Collection not found with ID: " + id);
            }
        }
    }

    @Override
    public void deleteCollection(Long id) {
        Optional<CollectionEntity> optionalCollection = repository.findById(id);

        if (optionalCollection.isPresent()) {
            CollectionEntity existingCollectionEntity = optionalCollection.get();
            repository.delete(existingCollectionEntity);
            log.info(COLLECTION_EVENTS,"Deleted collection with ID: {}", id);
        } else {
            log.warn(COLLECTION_EVENTS,"Collection not found for deletion with ID: {}", id);
            throw new EntityNotFoundException("Collection not found with ID: " + id);
        }
    }

    @Override
    public void deleteFromCollection(Long id, Long artworkId) {
        Optional<CollectionEntity> optionalCollection = repository.findById(id);

        Artwork artwork = artworkService.findArtworkById(artworkId);
        if (optionalCollection.isPresent()) {
            CollectionEntity existingCollectionEntity = optionalCollection.get();
            existingCollectionEntity.removeArtwork(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
            repository.save(existingCollectionEntity);
            log.info(COLLECTION_EVENTS,"Removed artwork with ID {} from collection with ID: {}", artwork.getId(), id);
        } else {
            log.warn(COLLECTION_EVENTS,"Collection not found for removing artwork with ID: {} from collection with ID: {}", artwork.getId(), id);
            throw new EntityNotFoundException("Collection not found with ID: " + id);
        }
    }

    @Override
    public void editName(Long id, String name) {
        Optional<CollectionEntity> optionalCollection = repository.findById(id);
        if (optionalCollection.isPresent()) {
            CollectionEntity existingCollectionEntity = optionalCollection.get();
            existingCollectionEntity.setTitle(name);
            repository.save(existingCollectionEntity);
            log.info(COLLECTION_EVENTS,"Updated collection name for collection with ID: {}", id);
        } else {
            log.warn(COLLECTION_EVENTS,"Collection not found for updating name with ID: {}", id);
            throw new EntityNotFoundException("Collection not found with ID: " + id);
        }
    }

    @Override
    public List<Collection> getAllCollections() {
        return repository.findAll().stream().map(CollectionMapper.INSTANCE::collectionEntityToCollection).collect(Collectors.toList());
    }
}