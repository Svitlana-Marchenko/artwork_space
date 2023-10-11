package com.system.artworkspace.rating;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RatingDto {
    @NotNull
    private Long id;

    @NotNull
    @Min(1)
    @Max(10)
    private double rate;

    @JsonProperty("curator_id")
    private Long curatorId;

    @Size(max = 5000)
    private String comment;

    public RatingDto() {
    }

    public RatingDto(Long id, double rate, Long curatorId, String comment) {
        this.id = id;
        this.rate = rate;
        this.curatorId = curatorId;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Long getCuratorId() {
        return curatorId;
    }

    public void setCuratorId(Long curatorId) {
        this.curatorId = curatorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
