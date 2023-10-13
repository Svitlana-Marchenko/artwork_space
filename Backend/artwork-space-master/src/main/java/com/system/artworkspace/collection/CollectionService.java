package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;

public interface CollectionService {
    CollectionDto createCollection(CollectionDto collection);

    void addToCollection(CollectionDto collection, ArtworkDto artwork);

    void deleteCollection (CollectionDto collection);

    void deleteFromCollection(CollectionDto collection, ArtworkDto artwork);

    void editName (CollectionDto collection, String name);

}
