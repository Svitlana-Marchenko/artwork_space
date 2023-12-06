package com.system.artworkspace.artwork.artworkUpdate;

public class ArtworkUpdateDto {

    private String title;
    private String description;
    private String technique;
    private double width;
    private double height;

    public ArtworkUpdateDto(String title, String description, String technique, double width, double height) {
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
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
}
