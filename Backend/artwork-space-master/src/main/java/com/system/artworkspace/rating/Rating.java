package com.system.artworkspace.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.artworkspace.user.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Rating {
    @NotNull
    private Long id;

    @NotNull
    @Min(1)
    @Max(10)
    private double rate;

   private User curator;

    @Size(max = 5000)
    private String comment;

    public Rating() {
    }

    public Rating(Long id, double rate, User curator, String comment) {
        this.id = id;
        this.rate = rate;
        this.curator = curator;
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

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
