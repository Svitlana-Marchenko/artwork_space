package com.system.artworkspace.collection;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.UserDto;
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
public class CollectionDto {

    private Long id;

    @NotNull(message = "Owner cant be null")
    private UserDto owner;

    @Size(max = 100, message = "Title cant be longer than 100 symbols")
    private String title;

    private List<ArtworkDto> artworks;

}