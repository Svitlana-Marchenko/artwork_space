package com.system.artworkspace.artwork.artworkUpdate;

import com.system.artworkspace.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkUpdateDto {

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

    private UserDto user;
}
