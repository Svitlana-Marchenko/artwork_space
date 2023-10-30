package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.Rating;

import java.util.List;

public interface ArtworkService {
    List<Artwork> getAllArtwork();

    Artwork addArtwork(Artwork artwork);

    void deleteArtwork(Long artworkId);

    Artwork findArtworkById(Long id);

    void updateTitle(Long id, String title);

    void updateDescription(Long id, String description);

    void updateTechnique(Long id, String technique);

    void updateWidth(Long id, double width);

    void updateHeight(Long id, double height);

    void updateImgUrl(Long id, String url);

    void updateImgSize(Long id, double size);

    List<Artwork> getArtworksByTitle(String title);

    List<Rating> getAllRating(Long id);

    void addRating(Long artworkId, Rating rating);

    void deleteRating(Long artworkId, Rating rating);
}
