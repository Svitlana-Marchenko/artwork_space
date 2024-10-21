package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Long id;

    @NotNull
    private User owner;

    @Size(max = 100)
    @NotBlank(message = "Title cant be blank")
    private String title;

    @NotEmpty
    private List<Artwork> artworks;
}
