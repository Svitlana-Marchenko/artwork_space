package com.system.artworkspace.rating;

import com.system.artworkspace.user.UserDto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Max(10)
    private double rate;

    @NotNull(message = "Curator can`t be null")
    private UserDto user;

    @Size(max = 5000, message = "Comment cant be longer than 5000 symbols")
    private String comment;

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
