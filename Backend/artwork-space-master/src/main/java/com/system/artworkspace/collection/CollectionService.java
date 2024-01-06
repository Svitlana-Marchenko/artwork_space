package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;

import java.util.List;

public interface CollectionService {
    Collection createCollection(Collection collection);
    Collection getCollectionById(Long id);
    List<Collection> getCollectionsByUserId(Long id);
    List<Artwork> getArtworksFromCollectionsByUserId(Long id);


    List<Artwork> getArtworksByCollection(Long id);

    void addToCollection(Long id, Long artworkId);

    void deleteCollection (Long id);

    void deleteFromCollection(Long id, Long artworkId);

    void editName (Long id, String name);

    List<Collection> getAllCollections();
}