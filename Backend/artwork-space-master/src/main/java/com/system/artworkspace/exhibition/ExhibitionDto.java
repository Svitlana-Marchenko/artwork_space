package com.system.artworkspace.exhibition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.validation.exhibition.ValidExhibitionDates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.Date;
import java.util.List;
@ValidExhibitionDates
public class ExhibitionDto {
    private Long id;
    @NotNull(message = "curator is null")
    private UserDto curator;

    @Size(max = 50, message = "name is longer than 50")
    @NotBlank(message = "name is blank")
    private String title;

    @Size(max = 2000, message = "description is longer than 2000")
    @NotBlank(message = "description is blank")
    private String description;

    @NotEmpty(message = "list of artwork id is empty")
    private List<ArtworkDto> artworks;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public ExhibitionDto() {
    }

    public ExhibitionDto(Long id, UserDto curator, String title, String description, List<ArtworkDto> artworks, Date startDate, Date endDate) {
        this.id = id;
        this.curator = curator;
        this.title = title;
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

    public List<ArtworkDto> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<ArtworkDto> artworks) {
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

    public UserDto getCurator() {
        return curator;
    }

    public void setCurator(UserDto curator) {
        this.curator = curator;
    }
}
