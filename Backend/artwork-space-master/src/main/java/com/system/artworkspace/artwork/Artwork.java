package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.user.Artist;
import com.system.artworkspace.user.User;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private User artist;
    private String imageURL;
    private double imageSize;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings;

    public Artwork(User artist, String title, String description, String technique,double width,double height,String imageURL,double imageSize) {
        this.artist = artist;
        this.title=title;
        this.width=width;
        this.height=height;
        this.imageURL=imageURL;
        this.technique=technique;
        this.description=description;
        this.imageSize=imageSize;
    }


    public Artwork(Long id) {
        this.id=id;
    }

    public Artwork() {

    }

    public ArtworkDto convertToArtworkDto(){
        return new ArtworkDto(id,title,description,technique,width,height, artist.getId(), imageURL,imageSize);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
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