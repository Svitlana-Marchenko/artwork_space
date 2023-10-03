package com.system.artworkspace.artwork;

public interface RatingAndCommentService {
    void addRating(Rating rating, Artwork artwork);
    void deleteRating(Rating rating, Artwork artwork);
    void editRating(Rating rating, String newComment, double newRate);
    double calculateGeneralRating(Artwork artwork);
}
