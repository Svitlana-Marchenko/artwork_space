package com.system.artworkspace.artwork;

import com.system.artworkspace.collection.Collection;
import com.system.artworkspace.exhibition.Exhibition;
import com.system.artworkspace.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByArtist(User user);
    List<Artwork> findByExhibition(Exhibition exhibition);
    List<Artwork> findByCollection(Collection collection);
    Optional<Artwork> findById(Long id);
    void deleteById(Long aLong);
}
