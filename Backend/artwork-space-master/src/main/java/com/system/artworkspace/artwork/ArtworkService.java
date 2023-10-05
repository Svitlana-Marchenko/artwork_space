package com.system.artworkspace.artwork;

import com.system.artworkspace.user.User;
import jakarta.persistence.ManyToOne;

public interface ArtworkService {
    Artwork addArtwork(Artwork artwork);
    void deleteArtwork(Long artworkId);
    Artwork findArtworkById(Long id);
    void updateTitle(Artwork artwork, String title);
    void updateDescription (Artwork artwork, String description);
    void updateTechnique (Artwork artwork, String technique);
    void updateWidth (Artwork artwork, double width);
    void updateHeight (Artwork artwork, double height);
    void updateImgUrl (Artwork artwork, String url);
    void updateImgSize (Artwork artwork, double size);


}
