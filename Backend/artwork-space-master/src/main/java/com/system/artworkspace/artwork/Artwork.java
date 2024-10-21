package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {

    private Long id;

    private String title;

    private String description;

    private String technique;

    private double width;

    private double height;

    private User user;

    private String imageURL;

    private List<Rating> ratings;

    public Artwork(String title, String description, String technique, double width, double height, User artist, String imageURL, List<Rating> ratings) {
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        this.imageURL = imageURL;
        this.user = artist;
        this.ratings=ratings;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Artwork artwork = (Artwork) obj;
        return Objects.equals(id, artwork.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

