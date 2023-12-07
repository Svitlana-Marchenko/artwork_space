package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CollectionDto {
    private Long id;

    @NotNull(message = "Owner cant be null")
    private UserDto owner;

    @Size(max = 100, message = "Title cant be longer than 100 symbols")
    private String title;
    private List<ArtworkDto> artworks;

    public CollectionDto() {
    }

    public CollectionDto(Long id, UserDto owner, String name, List<ArtworkDto> artworks) {
        this.id = id;
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

   public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public List<ArtworkDto> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<ArtworkDto> artworks) {
        this.artworks = artworks;
    }
}

