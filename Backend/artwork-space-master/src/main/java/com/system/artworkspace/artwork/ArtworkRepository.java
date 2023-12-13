package com.system.artworkspace.artwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {
    //List<Artwork> findByTitle(String title);
    @Modifying
    @Query("UPDATE ArtworkEntity a SET a.imageURL = :imageUrl WHERE a.id = :artworkId")
    void updateImageUrl(@Param("artworkId") Long artworkId, @Param("imageUrl") String imageUrl);

    List<ArtworkEntity> findAllByUserId(Long artistId);
}
