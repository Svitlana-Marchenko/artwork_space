package com.system.artworkspace.artwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    //List<Rating> findByArtwork(Artwork artwork);
}
