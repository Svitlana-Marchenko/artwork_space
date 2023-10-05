package com.system.artworkspace.artwork;

public interface RatingAndCommentService {
    Rating addRating(Rating rating, Artwork artwork);
    void deleteRating(Rating rating, Artwork artwork);
    double calculateGeneralRating(Artwork artwork);
}
