package com.system.artworkspace.collection;

import com.system.artworkspace.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class Collection {
    private Long id;

    @NotNull
    private User owner;

    @Size(max = 100)
    private String title;

    @NotEmpty
    private List<Long> artworkIds;

    public Collection() {
    }

    public Collection(Long id, User owner, String name, List<Long> artworkIds) {
        this.id = id;
        this.owner = owner;
        this.title = name;
        this.artworkIds = artworkIds;
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

    public List<Long> getArtworkIds() {
        return artworkIds;
    }

    public void setArtworkIds(List<Long> artworkIds) {
        this.artworkIds = artworkIds;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}

