package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ExhibitionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private UserEntity curator;
    private String title;
    @Column(length = 2000)
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "exhibition_artwork",
            joinColumns = @JoinColumn(name = "exhibition_id"),
            inverseJoinColumns = @JoinColumn(name = "artwork_id")
    )
    private List<ArtworkEntity> artworks;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;


    public ExhibitionEntity(UserEntity curator, String title, String description, List<ArtworkEntity> artworks, Date startDate, Date endDate) {
        this.curator = curator;
        this.title = title;
        this.description = description;
        this.artworks = artworks;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ExhibitionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurator(UserEntity curator) {
        this.curator = curator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtworks(List<ArtworkEntity> artworks) {
        this.artworks = artworks;
    }

    public UserEntity getCurator() {
        return curator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArtworkEntity> getArtworks() {
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
