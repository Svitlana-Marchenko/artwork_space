package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class CollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity owner;

    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ArtworkEntity> artworks;

    public CollectionEntity(String title, UserEntity owner){
        this.title=title;
        this.owner=owner;
        artworks = new LinkedList<>();
    }

    public void addNewArtwork(ArtworkEntity artworkEntity){
        artworks.add(artworkEntity);
    }

    public void removeArtwork(ArtworkEntity artworkEntity){
        artworks.remove(artworkEntity);
    }
}