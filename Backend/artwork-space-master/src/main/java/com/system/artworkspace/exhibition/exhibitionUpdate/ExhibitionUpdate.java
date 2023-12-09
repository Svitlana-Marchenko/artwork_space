package com.system.artworkspace.exhibition.exhibitionUpdate;

public class ExhibitionUpdate {

    private long id;

    private String title;

    private String description;

    public ExhibitionUpdate(Long id, String title, String description) {
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
