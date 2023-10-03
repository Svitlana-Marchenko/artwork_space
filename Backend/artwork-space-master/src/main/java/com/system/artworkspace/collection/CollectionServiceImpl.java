package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService{
    private final CollectionConfiguration collectionConfiguration;

    @Autowired
    public CollectionServiceImpl(CollectionConfiguration collectionConfiguration) {
        this.collectionConfiguration = collectionConfiguration;
    }
    @Override
    public Collection createCollection(String name, String description) {
        if(collectionConfiguration.isEnabled()){
            //--
        }
        return null;
    }

    @Override
    public void addToCollection(Collection collection, Artwork artwork) {
        if(collection.getArtworks().size()<collectionConfiguration.getMaxSize()){
            //--
        }
    }

    @Override
    public void deleteCollection(Collection collection) {

    }

    @Override
    public void deleteFromCollection(Collection collection, Artwork artwork) {

    }

    @Override
    public void editName(Collection collection, String name) {

    }

    @Override
    public void editDescription(Collection collection, String description) {

    }
}
