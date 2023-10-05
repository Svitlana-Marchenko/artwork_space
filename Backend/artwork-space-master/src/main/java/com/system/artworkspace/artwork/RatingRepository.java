package com.system.artworkspace.artwork;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByArtwork(Artwork artwork);
}
