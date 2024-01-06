package com.system.artworkspace.collection;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.*;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.system.artworkspace.logger.LoggingMarkers.COLLECTION_EVENTS;

@Service
public class CollectionServiceImpl implements CollectionService{
    private CollectionRepository repository;
    @Autowired
    private ArtworkService artworkService;
    private static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Autowired
    public CollectionServiceImpl(CollectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection createCollection(Collection collection) {
        repository.save(CollectionMapper.INSTANCE.collectionToCollectionEntity(collection));
        logger.info(COLLECTION_EVENTS,"Created collection with ID: {}", collection.getId());
        return collection;
    }

    @Override
    public Collection getCollectionById(Long id) {
        Optional<CollectionEntity> collection = repository.findById(id);
        logger.info("Finding collection by id (without CACHE)");
        if (collection.isPresent())
            return CollectionMapper.INSTANCE.collectionEntityToCollection(collection.get());
        else
            throw new EntityNotFoundException("Collection with id " + id + " not found");

    }

    @Override
    public List<Collection> getCollectionsByUserId(Long id) {
        List<Collection> cols =  repository.getCollectionEntitiesByOwnerId(id).stream().map(x -> CollectionMapper.INSTANCE.collectionEntityToCollection(x)).collect(Collectors.toList());
        return cols;
    }

    @Override
    public List<Artwork> getArtworksFromCollectionsByUserId(Long id) {
        List<Collection> collections = getCollectionsByUserId(id);
        List<List<Artwork>> listOfLists = new LinkedList<>();
        for(Collection col: collections){
            listOfLists.add(col.getArtworks());
        }
        List<Artwork> unionList = unionArtworkLists(listOfLists);

        return unionList;
    }

    @Override
    public List<Artwork> getArtworksByCollection(Long id) {
        return getCollectionById(id).getArtworks();
    }

    private List<Artwork> unionArtworkLists(List<List<Artwork>> listOfLists) {
        // Use Stream API to flatten the list of lists, then remove duplicates
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
                logger.info(COLLECTION_EVENTS,"Added artwork with ID {} to collection with ID: {}", artwork.getId(), id);
            } else {
                logger.warn(COLLECTION_EVENTS,"Collection not found for adding artwork with ID: {} to collection with ID: {}", artwork.getId(), id);
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
            logger.info(COLLECTION_EVENTS,"Deleted collection with ID: {}", id);
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for deletion with ID: {}", id);
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
            logger.info(COLLECTION_EVENTS,"Removed artwork with ID {} from collection with ID: {}", artwork.getId(), id);
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for removing artwork with ID: {} from collection with ID: {}", artwork.getId(), id);
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
            logger.info(COLLECTION_EVENTS,"Updated collection name for collection with ID: {}", id);
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for updating name with ID: {}", id);
            throw new EntityNotFoundException("Collection not found with ID: " + id);
        }
    }

    @Override
    public List<Collection> getAllCollections() {
        return repository.findAll().stream().map(x -> CollectionMapper.INSTANCE.collectionEntityToCollection(x)).collect(Collectors.toList());
    }

}