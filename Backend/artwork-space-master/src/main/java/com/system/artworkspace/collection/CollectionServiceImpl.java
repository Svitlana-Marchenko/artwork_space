package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService{
    private final CollectionConfiguration collectionConfiguration;

    private CollectionRepository repository;

    @Autowired
    public CollectionServiceImpl(CollectionConfiguration collectionConfiguration, CollectionRepository repository) {
        this.collectionConfiguration = collectionConfiguration;
        this.repository = repository;
    }
    @Override
    public Collection createCollection(Collection collection) {
        if(collectionConfiguration.isEnabled()){
           repository.save(collection);
        }
        return null;
    }

    @Override
    public void addToCollection(Collection collection, Artwork artwork) {
        if(collection.getArtworks().size()<collectionConfiguration.getMaxSize()){

                Optional<Collection> optionalCollection = repository.findById(collection.getId());

                if (optionalCollection.isPresent()) {
                    Collection existingCollection = optionalCollection.get();
                    existingCollection.addNewArtwork(artwork);
                    repository.save(existingCollection);
                } else {
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
        } else {
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
        } else {
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
        } else {
            throw new EntityNotFoundException("Collection not found with ID: " + collection.getId());
        }
    }

}
