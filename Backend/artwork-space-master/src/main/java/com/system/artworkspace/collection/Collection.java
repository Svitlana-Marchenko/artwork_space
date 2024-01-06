package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Collection {
    private Long id;

    @NotNull
    private User owner;

    @Size(max = 100)
    @NotBlank(message = "Title cant be blank")
    private String title;

    @NotEmpty
    private List<Artwork> artworks;

    public Collection() {
    }

    public Collection(Long id, User owner, String name, List<Artwork> artworks) {
        this.id = id;
        this.owner = owner;
        this.title = name;
        this.artworks = artworks;
    }
    public Collection(User owner, String name, List<Artwork> artworks) {
        this.owner = owner;
        this.title = name;
        this.artworks = artworks;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}
