package com.system.artworkspace.collection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CollectionDto {
    private Long id;

    @NotNull
    private Long ownerId;

    @Size(max = 100)
    private String name;

    @NotEmpty
    private List<Long> artworkIds;

    public CollectionDto() {
    }

    public CollectionDto(Long id, Long ownerId, String name, List<Long> artworkIds) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.artworkIds = artworkIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getArtworkIds() {
        return artworkIds;
    }

    public void setArtworkIds(List<Long> artworkIds) {
        this.artworkIds = artworkIds;
    }
}
