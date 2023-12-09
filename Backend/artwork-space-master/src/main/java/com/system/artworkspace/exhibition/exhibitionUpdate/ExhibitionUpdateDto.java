package com.system.artworkspace.exhibition.exhibitionUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ExhibitionUpdateDto {

    private long id;

    @Size(max = 50, message = "name is longer than 50")
    @NotBlank(message = "name is blank")
    private String title;

    @Size(max = 2000, message = "description is longer than 2000")
    @NotBlank(message = "description is blank")
    private String description;

    public ExhibitionUpdateDto(Long id, String title, String description) {
        this.id=id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
