package com.system.artworkspace.artwork;

import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdate;
import com.system.artworkspace.rating.Rating;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkService {
    List<Artwork> getAllArtwork();

    List<Artwork> getAllArtworkByArtistId(Long id);

    Artwork addArtwork(Artwork artwork,MultipartFile file);

    Artwork addArtwork(Artwork artwork);

    String saveImage(MultipartFile file);

    void deleteArtwork(Long artworkId);

    Artwork updateArtwork (Long id, ArtworkUpdate artworkUpdate);

    Artwork findArtworkById(Long id);

    Artwork updateTitle(Long id, String title);

    Artwork updateDescription(Long id, String description);

    Artwork updateTechnique(Long id, String technique);

    Artwork updateWidth(Long id, double width);

    Artwork updateHeight(Long id, double height);

    Artwork updateImgUrl(Long id, String url);

    List<Artwork> getArtworksByTitle(String title);

    List<Rating> getAllRating(Long id);

    Artwork addRating(Long artworkId, Rating rating);

    void deleteRating(Long artworkId, Rating rating);

    boolean existsRatingByCurator(Long curatorId, Long artworkId);

    boolean isSold(Long id);
}
