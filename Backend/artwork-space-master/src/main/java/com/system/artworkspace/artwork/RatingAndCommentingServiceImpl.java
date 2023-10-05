package com.system.artworkspace.artwork;

import org.rating.CountPriceService;
import org.rating.CountRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingAndCommentingServiceImpl implements RatingAndCommentService{
    @Autowired
    CountRatingService countRatingService;
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public double calculateGeneralRating(Artwork artwork) {
        return countRatingService.calculateRating(null);
    }

    @Override
    public Rating addRating(Rating rating, Artwork artwork) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Rating rating, Artwork artwork) {
        ratingRepository.delete(rating);
    }

}
