package com.system.artworkspace.artwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<ArtworkEntity, Long> {

    @Modifying
    @Query("UPDATE ArtworkEntity a SET a.imageURL = :imageUrl WHERE a.id = :artworkId")
    void updateImageUrl(@Param("artworkId") Long artworkId, @Param("imageUrl") String imageUrl);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM ArtworkEntity a " +
            "JOIN a.ratings r " +
            "WHERE a.id = :artworkId AND r.user.id = :userId")
    boolean existsRatingForUser(@Param("artworkId") Long artworkId, @Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM ArtworkEntity a WHERE a.user.id = :userId")
    void deleteArtworksByUserId(Long userId);
    List<ArtworkEntity> findAllByUserId(Long artistId);
}
