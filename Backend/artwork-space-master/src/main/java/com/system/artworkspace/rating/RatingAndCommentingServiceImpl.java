package com.system.artworkspace.rating;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingAndCommentingServiceImpl implements RatingAndCommentService{
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating addRating(Rating rating, Artwork artwork) {
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Rating rating, Artwork artwork) {
        ratingRepository.delete(rating);
    }

}
