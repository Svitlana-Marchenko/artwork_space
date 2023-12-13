package com.system.artworkspace.artwork.artworkUpdate;

import com.system.artworkspace.user.User;

public class ArtworkUpdate {

    private String title;
    private String description;
    private String technique;
    private double width;
    private double height;
    private User user;

    public ArtworkUpdate(String title, String description, String technique, double width, double height, User user) {
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        this.user = user;
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
}
