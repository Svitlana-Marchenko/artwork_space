package com.system.artworkspace.exhibition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
import com.system.artworkspace.validation.exhibition.ValidExhibitionDates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Setter
@Getter
@ValidExhibitionDates
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionDto {

    private Long id;

    @NotNull(message = "curator is null")
    private UserDto curator;

    @Size(max = 100, message = "name is longer than 100")
    @NotBlank(message = "name is blank")
    private String title;

    @Size(max = 2000, message = "description is longer than 2000")
    @NotBlank(message = "description is blank")
    private String description;

    @NotEmpty(message = "list of artwork id is empty")
    private List<ArtworkDto> artworks;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
