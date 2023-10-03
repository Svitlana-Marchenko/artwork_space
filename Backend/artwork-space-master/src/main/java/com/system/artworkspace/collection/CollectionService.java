package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.beans.factory.annotation.Autowired;

public interface CollectionService {

    Collection createCollection(String name, String description);

    void addToCollection(Collection collection, Artwork artwork);

    void deleteCollection (Collection collection);

    void deleteFromCollection(Collection collection, Artwork artwork);

    void editName (Collection collection, String name);

    void editDescription (Collection collection, String description);
}
