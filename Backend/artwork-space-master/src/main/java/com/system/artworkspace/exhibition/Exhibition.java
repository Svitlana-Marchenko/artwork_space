package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.Curator;
import com.system.artworkspace.user.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
@Entity
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
@ManyToOne
    private User curator;
    private String name;
    private String description;
    @OneToMany
    private List<Artwork> artworks;
    private Date startDate;
    private Date endDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public User getCurator() {
        return curator;
    }

    public void setCurator(Curator curator) {
        this.curator = curator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
