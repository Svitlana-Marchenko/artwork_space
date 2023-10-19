package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkEntity;
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
    @Column(length = 2000)
    private String description;
    @OneToMany
    private List<ArtworkEntity> artworkEntities;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;


    public Exhibition(User curator, String name, String description, List<ArtworkEntity> artworkEntities, Date startDate, Date endDate) {
        this.curator = curator;
        this.name = name;
        this.description = description;
        this.artworkEntities = artworkEntities;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Exhibition() {

    }

    public ExhibitionDto convertToExhibitionDto(){
        return new ExhibitionDto(id,curator.getId(),name,description,(List<Long>) artworkEntities.stream().map(x-> x.getId()),startDate,endDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurator(User curator) {
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
