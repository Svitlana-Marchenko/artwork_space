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
public class ArtworkServiceImpl implements ArtworkService{
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    private final ArtworkRepository repository;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArtworkDto addArtwork(ArtworkDto artwork) {
        logger.info(ARTWORK_EVENTS,"Adding artwork with ID: {}", artwork.getId());

        //repository.save(artwork.convertToArtwork());
        logger.info(ARTWORK_EVENTS,"Artwork added successfully.");

        return artwork;
    }

    @Override
    public void deleteArtwork(Long artworkId) {
        Optional<Artwork> optionalArtwork = repository.findById(artworkId);

        if (optionalArtwork.isPresent()) {
            repository.deleteById(artworkId);
            logger.info(ARTWORK_EVENTS,"Artwork deleted with ID: {}", artworkId);
        } else {
            logger.warn(ARTWORK_EVENTS,"Artwork not found for deletion with ID: {}", artworkId);
            throw new EntityNotFoundException("Artwork not found with ID: " + artworkId);
        }
    }


    @Override
    public ArtworkDto findArtworkById(Long id) {
        Optional<Artwork> artwork = repository.findById(id);
        Artwork art = artwork.orElse(null);
        return art.convertToArtworkDto();
    }

    @Override
    public void updateTitle(ArtworkDto artwork, String title) {
        ThreadContext.put("artwork_id", artwork.getId().toString());
        ArtworkValidator.validateTitle(title);
        ThreadContext.clearAll();

        /*Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();

            ThreadContext.put("artwork_id", artwork.getId().toString());
            ArtworkValidator.validateTitle(title);
            ThreadContext.clearAll();

            existingArtwork.setTitle(title);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Title updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }*/
    }

    @Override
    public void updateDescription(ArtworkDto artwork, String description) {


        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setDescription(description);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Description updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateTechnique(ArtworkDto artwork, String technique) {

        ThreadContext.put("artwork_id", artwork.getId().toString());
        ArtworkValidator.validateTechnique(technique);
        ThreadContext.clearAll();

        /*Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setTechnique(technique);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Technique updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }*/
    }

    @Override
    public void updateWidth(ArtworkDto artwork, double width) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setWidth(width);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Width updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateHeight(ArtworkDto artwork, double height) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setHeight(height);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Height updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateImgUrl(ArtworkDto artwork, String url) {
        ThreadContext.put("artwork_id", artwork.getId().toString());
        ArtworkValidator.validateImageURL(url);
        ThreadContext.clearAll();


        /*Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setImageURL(url);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Image URL updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }*/
    }

    @Override
    public void updateImgSize(ArtworkDto artwork, double size) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork = optionalArtwork.get();
            existingArtwork.setImageSize(size);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Image Size updated for artwork with ID: {}", artwork.getId());
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }


    public List<ArtworkDto> getArtworksByTitle(String title) {
        Specification<Artwork> titleSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), title);

        List<Artwork> list = repository.findAll((Sort) titleSpecification);
        return (List<ArtworkDto>)list.stream().map(x -> x.convertToArtworkDto());
        //return repository.findByTitle(title);
    }

}
