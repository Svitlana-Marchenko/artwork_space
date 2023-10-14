package com.system.artworkspace.artwork;

import com.system.artworkspace.user.User;
import jakarta.persistence.ManyToOne;

import java.util.List;

public interface ArtworkService {
    ArtworkDto addArtwork(ArtworkDto artwork);
    void deleteArtwork(Long artworkId);
    ArtworkDto findArtworkById(Long id);
    void updateTitle(ArtworkDto artwork, String title);
    void updateDescription (ArtworkDto artwork, String description);
    void updateTechnique (ArtworkDto artwork, String technique);
    void updateWidth (ArtworkDto artwork, double width);
    void updateHeight (ArtworkDto artwork, double height);
    void updateImgUrl (ArtworkDto artwork, String url);
    void updateImgSize (ArtworkDto artwork, double size);
     List<ArtworkDto> getArtworksByTitle(String title);
}
