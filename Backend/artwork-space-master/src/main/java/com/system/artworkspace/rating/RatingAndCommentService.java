package com.system.artworkspace.rating;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;

public interface RatingAndCommentService {
    RatingDto addRating(RatingDto rating, ArtworkDto artwork);
    void deleteRating(RatingDto rating, ArtworkDto artwork);
}
