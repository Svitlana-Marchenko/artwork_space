package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.User;

import java.util.List;
import java.util.Set;

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

    public Artwork() {
    }
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

    public Artwork(Long id, String title, String description, String technique, double width, double height, User artist, String imageURL, List<Rating> ratings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        this.imageURL = imageURL;
        this.user = artist;
        this.ratings=ratings;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}

