package com.system.artworkspace.artwork;

public interface ArtworkService {
    Artwork addArtwork(Artwork artwork);
    Artwork updateArtwork(Artwork artwork);
    void deleteArtwork(String artworkId);
    Artwork findArtworkById(String id);
    String generateUniqueArtworkId();
}
