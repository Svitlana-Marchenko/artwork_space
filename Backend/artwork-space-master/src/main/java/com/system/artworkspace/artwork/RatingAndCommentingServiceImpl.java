package com.system.artworkspace.artwork;

import org.rating.CountPriceService;
import org.rating.CountRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("org.rating")
public class RatingAndCommentingServiceImpl implements RatingAndCommentService{

   private CountRatingService countRatingService;
    private RatingRepository repository;

    @Autowired
    public RatingAndCommentingServiceImpl(RatingRepository repository, CountRatingService countRatingService) {
        this.repository = repository;
        this.countRatingService = countRatingService;
    }

    @Override
    public double calculateGeneralRating(Artwork artwork) {
        return countRatingService.calculateRating(null);
    }

    //TODO do it
    @Override
    public void addRating(Rating rating, Artwork artwork) {

    }

    //todo do it
    @Override
    public void deleteRating(Rating rating, Artwork artwork) {

    }

    @Override
    public void editRating(Rating rating, String newComment, double newRate) {
        Rating existingRating = repository.findById(rating.getId()).orElse(null);

        if (existingRating != null) {
            existingRating.setComment(newComment);
            existingRating.setRate(newRate);

            repository.save(existingRating);
        } else {
            throw new RuntimeException("Rating not found with ID: " + rating.getId());
        }
    }

}
