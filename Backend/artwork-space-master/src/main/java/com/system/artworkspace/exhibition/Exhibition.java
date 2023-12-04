package com.system.artworkspace.exhibition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
public class Exhibition {
    private Long id;

    @Size(max = 50, message = "name is longer than 50")
    @NotBlank(message = "name is blank")
    private String title;

    @Size(max = 2000, message = "description is longer than 2000")
    @NotBlank(message = "description is blank")
    private String description;

    @NotNull(message = "curator is null")
    private User curator;

    @NotEmpty(message = "list of artwork id is empty")
    private List<Artwork> artworks;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Exhibition() {
    }

    public Exhibition(Long id, User curator, String title, String description, List<Artwork> artworks, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.curator = curator;
        this.description = description;
        this.artworks = artworks;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
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

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }
}
