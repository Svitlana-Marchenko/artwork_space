package com.system.artworkspace.artwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    //List<Artwork> findByTitle(String title);
    List<ArtworkEntity> findAllByUserId(Long artistId);
}
