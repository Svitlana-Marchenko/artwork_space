package com.system.artworkspace.exhibition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.user.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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
    private List<Long> artworkIds;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date endDate;

    public ExhibitionDto() {
    }

    public ExhibitionDto(Long id, UserDto curator, String name, String description, List<Long> artworkIds, Date startDate, Date endDate) {
        this.id = id;
        this.curator = curator;
        this.title = name;
        this.description = description;
        this.artworkIds = artworkIds;
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

    public List<Long> getArtworkIds() {
        return artworkIds;
    }

    public void setArtworkIds(List<Long> artworkIds) {
        this.artworkIds = artworkIds;
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
