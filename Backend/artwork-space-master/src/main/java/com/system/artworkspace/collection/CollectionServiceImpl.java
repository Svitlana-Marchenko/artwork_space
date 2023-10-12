package com.system.artworkspace.collection;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.COLLECTION_EVENTS;

@Service
public class CollectionServiceImpl implements CollectionService{
    private final CollectionConfiguration collectionConfiguration;

    private CollectionRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Autowired
    public CollectionServiceImpl(CollectionConfiguration collectionConfiguration, CollectionRepository repository) {
        this.collectionConfiguration = collectionConfiguration;
        this.repository = repository;
    }

    @Override
    public Collection createCollection(Collection collection) {
        if (collectionConfiguration.isEnabled()) {
            repository.save(collection);
            logger.info(COLLECTION_EVENTS,"Created collection with ID: {}", collection.getId());
        }
        return collection;
    }

    @Override
    public void addToCollection(Collection collection, Artwork artwork) {
        if (collection.getArtworks().size() < collectionConfiguration.getMaxSize()) {
            Optional<Collection> optionalCollection = repository.findById(collection.getId());

            if (optionalCollection.isPresent()) {
                Collection existingCollection = optionalCollection.get();
                existingCollection.addNewArtwork(artwork);
                repository.save(existingCollection);
                logger.info(COLLECTION_EVENTS,"Added artwork with ID {} to collection with ID: {}", artwork.getId(), collection.getId());
            } else {
                logger.warn(COLLECTION_EVENTS,"Collection not found for adding artwork with ID: {} to collection with ID: {}", artwork.getId(), collection.getId());
                throw new EntityNotFoundException("Collection not found with ID: " + collection.getId());
            }
        }
    }

    @Override
    public void deleteCollection(Collection collection) {
        Optional<Collection> optionalCollection = repository.findById(collection.getId());

        if (optionalCollection.isPresent()) {
            Collection existingCollection = optionalCollection.get();
            repository.delete(existingCollection);
            logger.info(COLLECTION_EVENTS,"Deleted collection with ID: {}", collection.getId());
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for deletion with ID: {}", collection.getId());
            throw new EntityNotFoundException("Collection not found with ID: " + collection.getId());
        }
    }

    @Override
    public void deleteFromCollection(Collection collection, Artwork artwork) {
        Optional<Collection> optionalCollection = repository.findById(collection.getId());

        if (optionalCollection.isPresent()) {
            Collection existingCollection = optionalCollection.get();
            existingCollection.removeArtwork(artwork);
            repository.save(existingCollection);
            logger.info(COLLECTION_EVENTS,"Removed artwork with ID {} from collection with ID: {}", artwork.getId(), collection.getId());
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for removing artwork with ID: {} from collection with ID: {}", artwork.getId(), collection.getId());
            throw new EntityNotFoundException("Collection not found with ID: " + collection.getId());
        }
    }

    @Override
    public void editName(Collection collection, String name) {
        Optional<Collection> optionalCollection = repository.findById(collection.getId());
        if (optionalCollection.isPresent()) {
            Collection existingCollection = optionalCollection.get();
            existingCollection.setName(name);
            repository.save(existingCollection);
            logger.info(COLLECTION_EVENTS,"Updated collection name for collection with ID: {}", collection.getId());
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for updating name with ID: {}", collection.getId());
            throw new EntityNotFoundException("Collection not found with ID: " + collection.getId());
        }
    }

}
