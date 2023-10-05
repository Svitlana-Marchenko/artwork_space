package com.system.artworkspace.artwork;

public interface RatingAndCommentService {
    Rating addRating(Rating rating, Artwork artwork);
    void deleteRating(Rating rating, Artwork artwork);
    Rating updateRating(Rating rating, String newComment, double newRate);
    double calculateGeneralRating(Artwork artwork);
}
