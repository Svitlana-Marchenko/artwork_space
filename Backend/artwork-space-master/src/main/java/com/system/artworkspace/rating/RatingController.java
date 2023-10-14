package com.system.artworkspace.rating;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public RatingDto addRating(@RequestBody RatingDto rating, @RequestParam Long artworkId) {
        logger.info("Adding rating for artwork with ID: {}", artworkId);
//        RatingDto addedRating = ratingService.addRating(rating, artworkId);
//        logger.info("Rating added with ID {} for artwork with ID: {}", addedRating.getId(), artworkId);
//        return addedRating;
        return null;
    }

    @DeleteMapping("/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        logger.info("Deleting rating with ID: {}", ratingId);
        //ratingService.deleteRating(ratingId);
        logger.info("Rating deleted with ID: {}", ratingId);
    }
}
