package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;

import java.util.List;
public class Collection {
    private String collectionId;
    private Collectioneer owner;
    private String name;
    private List<Artwork> artworks;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(Collectioneer owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }
}
