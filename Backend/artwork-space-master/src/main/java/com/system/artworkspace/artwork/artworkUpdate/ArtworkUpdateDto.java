package com.system.artworkspace.artwork.artworkUpdate;

import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ArtworkUpdateDto {
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
    private UserDto user;

    public ArtworkUpdateDto(String title, String description, String technique, double width, double height, UserDto user) {
        this.title = title;
        this.description = description;
        this.technique = technique;
        this.width = width;
        this.height = height;
        this.user=user;
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
}
