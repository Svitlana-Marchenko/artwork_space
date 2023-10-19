package com.system.artworkspace.collection;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
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
            repository.save(CollectionMapper.INSTANCE.collectionToCollectionEntity(collection));
            logger.info(COLLECTION_EVENTS,"Created collection with ID: {}", collection.getId());
        }
        return collection;
    }

    @Override
    public void addToCollection(Long id, Artwork artwork) {
        Optional<CollectionEntity> collectionEntity = repository.findById(id);
         if (collectionEntity.isPresent() && collectionEntity.get().getArtworks().size() < collectionConfiguration.getMaxSize()) {
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
    public void deleteFromCollection(Long id, Artwork artwork) {
        Optional<CollectionEntity> optionalCollection = repository.findById(id);

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
            existingCollectionEntity.setName(name);
            repository.save(existingCollectionEntity);
            logger.info(COLLECTION_EVENTS,"Updated collection name for collection with ID: {}", id);
        } else {
            logger.warn(COLLECTION_EVENTS,"Collection not found for updating name with ID: {}", id);
            throw new EntityNotFoundException("Collection not found with ID: " + id);
        }
    }

}
