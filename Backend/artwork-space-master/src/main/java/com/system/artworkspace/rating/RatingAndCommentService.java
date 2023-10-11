package com.system.artworkspace.rating;

import com.system.artworkspace.artwork.Artwork;

public interface RatingAndCommentService {
    Rating addRating(Rating rating, Artwork artwork);
    void deleteRating(Rating rating, Artwork artwork);
}
