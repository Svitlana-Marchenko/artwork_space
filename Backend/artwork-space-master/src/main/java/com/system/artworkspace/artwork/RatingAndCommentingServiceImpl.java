package com.system.artworkspace.artwork;

import org.rating.CountPriceService;
import org.rating.CountRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingAndCommentingServiceImpl implements RatingAndCommentService{
    @Autowired
    CountRatingService countRatingService;

    @Override
    public double calculateGeneralRating(Artwork artwork) {
        return countRatingService.calculateRating(null);
    }

    @Override
    public void addRating(Rating rating, Artwork artwork) {

    }

    @Override
    public void deleteRating(Rating rating, Artwork artwork) {

    }

    @Override
    public void editRating(Rating rating, String newComment, double newRate) {

    }

}
