package com.system.artworkspace.artwork;

import com.system.artworkspace.ArtworkSpaceApplication;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ArtworkServiceImpl implements ArtworkService{
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    private final ArtworkRepository repository;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Artwork addArtwork(Artwork artwork) {
        logger.info("Adding artwork with ID: {}", artwork.getId());

        repository.save(artwork);
        logger.info("Artwork added successfully.");

        return artwork;
    }

    @Override
    public void deleteArtwork(Long artworkId) {
        Optional<Artwork> optionalArtwork = repository.findById(artworkId);

        if (optionalArtwork.isPresent()) {
            repository.deleteById(artworkId);
            logger.info("Artwork deleted with ID: {}", artworkId);
        } else {
            logger.warn("Artwork not found for deletion with ID: {}", artworkId);
            throw new EntityNotFoundException("Artwork not found with ID: " + artworkId);
        }
    }


    @Override
    public Artwork findArtworkById(Long id) {
        Optional<Artwork> artwork = repository.findById(id);
        return artwork.orElse(null);
    }

    @Override
    public void updateTitle(Artwork artwork, String title) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setTitle(title);
            repository.save(existingArtwork);
            logger.info("Title updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateDescription(Artwork artwork, String description) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setDescription(description);
            repository.save(existingArtwork);
            logger.info("Description updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateTechnique(Artwork artwork, String technique) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setTechnique(technique);
            repository.save(existingArtwork);
            logger.info("Technique updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateWidth(Artwork artwork, double width) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setWidth(width);
            repository.save(existingArtwork);
            logger.info("Width updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateHeight(Artwork artwork, double height) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setHeight(height);
            repository.save(existingArtwork);
            logger.info("Height updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateImgUrl(Artwork artwork, String url) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setImageURL(url);
            repository.save(existingArtwork);
            logger.info("Image URL updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateImgSize(Artwork artwork, double size) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setImageSize(size);
            repository.save(existingArtwork);
            logger.info("Image Size updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }


    public List<Artwork> getArtworksByTitle(String title) {
        Specification<Artwork> titleSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), title);

        return repository.findAll((Sort) titleSpecification);
        //return repository.findByTitle(title);
    }

}
