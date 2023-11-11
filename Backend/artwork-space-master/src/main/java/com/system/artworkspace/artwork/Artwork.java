package com.system.artworkspace.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.artworkspace.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class Artwork {
    private Long id;
    private String title;
//for test
    private String description;
    private String technique;
    private double width;
    private double height;
    //private Long artistId;
    private User artist;
    private String imageURL;
    private double imageSize;

    public Artwork() {
    }

    public Artwork(Long id, String title, String description, String technique, double width, double height, User artist, String imageURL, double imageSize) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        //this.artistId = artistId;
        this.imageURL = imageURL;
        this.imageSize = imageSize;
        this.artist = artist;
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

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getImageSize() {
        return imageSize;
    }

    public void setImageSize(double imageSize) {
        this.imageSize = imageSize;
    }
}

