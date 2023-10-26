package com.system.artworkspace.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Long artistId;
    @NotBlank(message = "image URL is blank")
   // @JsonProperty("artist_id")
    private String imageURL;
    private double imageSize;

    public ArtworkDto() {
    }

    public ArtworkDto(Long id, String title, String description, String technique, double width, double height, Long artistId, String imageURL, double imageSize) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        this.artistId = artistId;
        this.imageURL = imageURL;
        this.imageSize = imageSize;
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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
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
