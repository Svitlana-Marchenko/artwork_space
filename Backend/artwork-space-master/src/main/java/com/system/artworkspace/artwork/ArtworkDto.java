package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public class ArtworkDto {
    private Long id;
    @NotBlank(message = "title is blank")
    @Size(max = 50, message = "title is longer than 50")
    private String title;
    @NotBlank(message = "description is blank")
    @Size(max = 2000, message = "description is longer than 2000")
    private String description;
    @NotBlank(message = "technique is blank")
    @Size(max = 100, message = "technique is longer than 100")
    private String technique;
    private double width;
    private double height;
    @NotNull(message = "artist id is null")
    private UserDto user;
    private String imageURL;
    private double imageSize;

    private List<RatingDto> ratings;
    public ArtworkDto() {
    }

    public ArtworkDto(Long id, String title, String description, String technique, double width, double height, UserDto artist, String imageURL, double imageSize,  List<RatingDto> ratings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        this.user =artist;
        this.imageURL = imageURL;
        this.imageSize = imageSize;
        this.ratings=ratings;
    }

    public List<RatingDto> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingDto> ratings) {
        this.ratings = ratings;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
