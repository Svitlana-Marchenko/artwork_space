package com.system.artworkspace.rating;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingAndCommentingServiceImpl implements RatingAndCommentService{
    @Autowired
    private RatingRepository ratingRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Override
    public Rating addRating(Rating rating, Artwork artwork) {
        logger.info("Adding rating for artwork with ID: {}", artwork.getId());
        //rating.setArtwork(artwork);
        Rating savedRating = ratingRepository.save(rating);
        logger.info("Added rating with ID {} for artwork with ID: {}", savedRating.getId(), artwork.getId());
        return savedRating;
    }

    @Override
    public void deleteRating(Rating rating, Artwork artwork) {
        if (rating != null) {
            ratingRepository.delete(rating);
            logger.info("Deleted rating with ID {} for artwork with ID: {}", rating.getId(), artwork.getId());
        } else {
            logger.warn("Rating not found for deletion.");
            throw new EntityNotFoundException("Rating not found when deleting.");
        }
    }
}
