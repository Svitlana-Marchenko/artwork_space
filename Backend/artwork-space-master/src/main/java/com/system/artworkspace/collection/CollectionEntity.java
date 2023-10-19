package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private UserEntity owner;
    private String name;
    @ManyToMany
    private List<ArtworkEntity> artworkEntities;

    public CollectionEntity(){}

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
        this.artworkEntities = artworkEntities;
    }

    public UserEntity getOwner() {
        return owner;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArtworkEntity> getArtworks() {
        return artworkEntities;
    }

    public void addNewArtwork(ArtworkEntity artworkEntity){
        artworkEntities.add(artworkEntity);
    }

    public void removeArtwork(ArtworkEntity artworkEntity){
        artworkEntities.remove(artworkEntity);
    }
}
