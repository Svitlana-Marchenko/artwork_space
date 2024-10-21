package com.system.artworkspace.exhibition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Exhibition {

    private Long id;

    @Size(max = 50, message = "name is longer than 50")
    @NotBlank(message = "name is blank")
    private String title;

    @Size(max = 2000, message = "description is longer than 2000")
    @NotBlank(message = "description is blank")
    private String description;

    @NotNull(message = "curator is null")
    private User curator;

    @NotEmpty(message = "list of artwork id is empty")
    private List<Artwork> artworks;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
