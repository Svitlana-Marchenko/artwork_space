package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.Rating;

import java.util.List;

public interface ArtworkService {
    List<Artwork> getAllArtwork();

    Artwork addArtwork(Artwork artwork);

    void deleteArtwork(Long artworkId);

    Artwork findArtworkById(Long id);

    Artwork updateTitle(Long id, String title);

    Artwork updateDescription(Long id, String description);

    Artwork updateTechnique(Long id, String technique);

    Artwork updateWidth(Long id, double width);

    Artwork updateHeight(Long id, double height);

    Artwork updateImgUrl(Long id, String url);

    Artwork updateImgSize(Long id, double size);

    List<Artwork> getArtworksByTitle(String title);

    List<Rating> getAllRating(Long id);

    void addRating(Long artworkId, Rating rating);

    void deleteRating(Long artworkId, Rating rating);
}
