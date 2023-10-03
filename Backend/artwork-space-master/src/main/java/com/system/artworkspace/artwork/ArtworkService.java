package com.system.artworkspace.artwork;

public interface ArtworkService {
    void addArtwork(Artwork artwork);
    void editArtwork(Artwork artwork);
    void deleteArtwork(String artworkId);
    Artwork findArtworkById(String id);
}
