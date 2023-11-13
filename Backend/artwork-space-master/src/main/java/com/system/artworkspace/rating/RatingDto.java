package com.system.artworkspace.rating;

import com.system.artworkspace.user.UserDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RatingDto {
    @NotNull
    private Long id;

    @NotNull
    @Min(1)
    @Max(10)
    private double rate;

    private UserDto user;

    @Size(max = 5000)
    private String comment;

    public RatingDto() {
    }

    public RatingDto(Long id, double rate, UserDto curator, String comment) {
        this.id = id;
        this.rate = rate;
        this.user = curator;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
        RatingDto ratingDto = (RatingDto) o;
        return Objects.equals(id, ratingDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
