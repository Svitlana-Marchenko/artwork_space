package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class ArtworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String title;
    @Column(length = 2000)
    private String description;
    private String technique;
    private double width;
    private double height;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private String imageURL;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RatingEntity> ratings;

    public ArtworkEntity(UserEntity artist, String title, String description, String technique, double width, double height, String imageURL, List<RatingEntity> ratings) {
        this.user = artist;
        this.title=title;
        this.width=width;
        this.height=height;
        this.imageURL=imageURL;
        this.technique=technique;
        this.description=description;
        this.ratings=ratings;
    }


    public ArtworkEntity(Long id) {
        this.id=id;
    }

    public ArtworkEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RatingEntity> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingEntity> ratings) {
        this.ratings = ratings;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity artist) {
        this.user = artist;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}