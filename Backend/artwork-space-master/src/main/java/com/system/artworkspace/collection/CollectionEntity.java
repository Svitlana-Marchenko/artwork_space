package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
@Entity
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity owner;
    private String title;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<ArtworkEntity> artworks;


    public CollectionEntity(){}

    public CollectionEntity(String title, UserEntity owner){
        this.title=title;
        this.owner=owner;
        artworks = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public void setArtworks(List<ArtworkEntity> artworkEntities) {
        this.artworks = artworkEntities;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public List<ArtworkEntity> getArtworks() {
        return artworks;
    }

    public void addNewArtwork(ArtworkEntity artworkEntity){
        artworks.add(artworkEntity);
    }

    public void removeArtwork(ArtworkEntity artworkEntity){
        artworks.remove(artworkEntity);
    }
    public void removeArtworkById(Long id){
        //artworks.
    }
}