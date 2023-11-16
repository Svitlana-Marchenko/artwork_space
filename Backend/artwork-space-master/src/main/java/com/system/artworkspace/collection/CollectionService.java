package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;

import java.util.List;

public interface CollectionService {
    Collection createCollection(Collection collection);

    void addToCollection(Long id, Artwork artwork);

    void deleteCollection (Long id);

    void deleteFromCollection(Long id, Artwork artwork);

    void editName (Long id, String name);

    List<Collection> getAllCollections();
}
