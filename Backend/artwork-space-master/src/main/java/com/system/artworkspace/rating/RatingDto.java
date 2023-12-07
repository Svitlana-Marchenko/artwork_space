package com.system.artworkspace.rating;

import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.*;


import java.util.Objects;

public class RatingDto {

    private Long id;

    @NotBlank(message = "Rating cant be blank")
    @Min(1)
    @Max(10)
    private double rate;

    private UserDto user;

    @Size(max = 5000, message = "Comment cant be longer than 5000 symbols")
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
