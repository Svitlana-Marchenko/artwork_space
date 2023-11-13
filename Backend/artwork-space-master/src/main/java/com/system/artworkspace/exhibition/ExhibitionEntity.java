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
    @OneToMany
    private List<ArtworkEntity> artworkEntities;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;


    public ExhibitionEntity(UserEntity curator, String name, String description, List<ArtworkEntity> artworkEntities, Date startDate, Date endDate) {
        this.curator = curator;
        this.title = name;
        this.description = description;
        this.artworkEntities = artworkEntities;
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

    public void setArtworks(List<ArtworkEntity> artworkEntities) {
        this.artworkEntities = artworkEntities;
    }

    public UserEntity getCurator() {
        return curator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public List<ArtworkEntity> getArtworks() {
        return artworkEntities;
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
