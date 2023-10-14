package com.system.artworkspace.rating;

import com.system.artworkspace.artwork.ArtworkDto;

public interface RatingService {
    RatingDto addRating(RatingDto rating, ArtworkDto artwork);
    void deleteRating(RatingDto rating, ArtworkDto artwork);
}
