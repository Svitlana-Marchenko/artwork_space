package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.Collectioneer;
import com.system.artworkspace.user.User;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User owner;
    private String name;
    @ManyToMany
    private List<Artwork> artworks;

    public Collection(){}

    public CollectionDto convertToCollectionDto(){
        return new CollectionDto(id, owner.getId(), name, (List<Long>)artworks.stream().map(x -> x.getId()));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public void addNewArtwork(Artwork artwork){
        artworks.add(artwork);
    }

    public void removeArtwork(Artwork artwork){
        artworks.remove(artwork);
    }
}
