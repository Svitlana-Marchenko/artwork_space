package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkDto {

    private Long id;

    @NotBlank(message = "title is blank")
    @Size(max = 50, message = "title is longer than 50")
    private String title;

    @NotBlank(message = "description is blank")
    @Size(max = 2000, message = "description is longer than 2000")
    private String description;

    @NotBlank(message = "technique is blank")
    @Size(max = 100, message = "technique is longer than 100")
    private String technique;

    private double width;

    private double height;

    @NotNull(message = "artist id is null")
    private UserDto user;

    private String imageURL;

    private List<RatingDto> ratings;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArtworkDto artwork = (ArtworkDto) obj;
        return Objects.equals(id, artwork.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
