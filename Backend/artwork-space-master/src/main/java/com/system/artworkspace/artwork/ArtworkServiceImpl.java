package com.system.artworkspace.artwork;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.validation.ArtworkValidator;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.ARTWORK_EVENTS;


@Service
public class ArtworkServiceImpl implements ArtworkService {
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    private final ArtworkRepository repository;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Artwork addArtwork(Artwork artwork) {
        logger.info(ARTWORK_EVENTS, "Adding artwork with ID: {}", artwork.getId());
        repository.save(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
        logger.info(ARTWORK_EVENTS, "Artwork added successfully.");

        return artwork;
    }

    @Override
    public void deleteArtwork(Long artworkId) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(artworkId);

        if (optionalArtwork.isPresent()) {
            repository.deleteById(artworkId);
            logger.info(ARTWORK_EVENTS, "Artwork deleted with ID: {}", artworkId);
        } else {
            logger.warn(ARTWORK_EVENTS, "Artwork not found for deletion with ID: {}", artworkId);
            throw new EntityNotFoundException("Artwork not found with ID: " + artworkId);
        }
    }

    @Override
    public Artwork findArtworkById(Long id) {
        Optional<ArtworkEntity> artwork = repository.findById(id);
        if (artwork.isPresent())
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(artwork.get());
        return null;
    }

    @Override
    public void updateTitle(Long id, String title) {
        ThreadContext.put("artwork_id", id.toString());
        ArtworkValidator.validateTitle(title);
        ThreadContext.clearAll();

        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork= optionalArtwork.get();

            ThreadContext.put("artwork_id", id.toString());
            ArtworkValidator.validateTitle(title);
            ThreadContext.clearAll();

            existingArtwork.setTitle(title);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Title updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void updateDescription(Long id, String description) {


        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setDescription(description);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Description updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void updateTechnique(Long id, String technique) {

        ThreadContext.put("artwork_id", id.toString());
        ArtworkValidator.validateTechnique(technique);
        ThreadContext.clearAll();

        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setTechnique(technique);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Technique updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void updateWidth(Long id, double width) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setWidth(width);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Width updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void updateHeight(Long id, double height) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setHeight(height);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Height updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void updateImgUrl(Long id, String url) {
        ThreadContext.put("artwork_id", id.toString());
        ArtworkValidator.validateImageURL(url);
        ThreadContext.clearAll();


        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setImageURL(url);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Image URL updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void updateImgSize(Long id, double size) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setImageSize(size);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Image Size updated for artwork with ID: {}", id);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }


    public List<Artwork> getArtworksByTitle(String title) {
        Specification<ArtworkEntity> titleSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), title);

        List<ArtworkEntity> list = repository.findAll((Sort) titleSpecification);
        return (List<Artwork>) list.stream().map(x -> ArtworkMapper.INSTANCE.artworkEntityToArtwork(x));
    }

}
