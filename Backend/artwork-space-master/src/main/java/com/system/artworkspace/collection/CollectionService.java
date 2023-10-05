package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
public interface CollectionService {
    Collection createCollection(Collection collection);

    void addToCollection(Collection collection, Artwork artwork);

    void deleteCollection (Collection collection);

    void deleteFromCollection(Collection collection, Artwork artwork);

    void editName (Collection collection, String name);

}
