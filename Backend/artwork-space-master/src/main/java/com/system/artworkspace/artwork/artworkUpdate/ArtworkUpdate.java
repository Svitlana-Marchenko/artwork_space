package com.system.artworkspace.artwork.artworkUpdate;

import com.system.artworkspace.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ArtworkUpdate {

    private String title;

    private String description;

    private String technique;

    private double width;

    private double height;

    private User user;
}
