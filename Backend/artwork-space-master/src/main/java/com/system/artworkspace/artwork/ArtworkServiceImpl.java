package com.system.artworkspace.artwork;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdate;
import com.system.artworkspace.collection.CollectionEntity;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import com.system.artworkspace.exhibition.ExhibitionEntity;
import com.system.artworkspace.exhibition.ExhibitionMapper;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.rating.RatingMapper;
import com.system.artworkspace.validation.ArtworkValidator;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.*;
import static com.system.artworkspace.logger.LoggingMarkers.COLLECTION_EVENTS;


@Service
public class ArtworkServiceImpl implements ArtworkService {
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    private final ArtworkRepository repository;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Artwork> getAllArtwork() {
        return repository.findAll().stream().map(x -> ArtworkMapper.INSTANCE.artworkEntityToArtwork(x)).collect(Collectors.toList());
    }

    @Override
    public List<Artwork> getAllArtworkByArtistId(Long id) {
        return repository.findAllByUserId(id).stream().map(x -> ArtworkMapper.INSTANCE.artworkEntityToArtwork(x)).collect(Collectors.toList());

    }

    @Override
    public Artwork addArtwork(Artwork artwork) {
        logger.info(ARTWORK_EVENTS, "Adding artwork with ID: {}", artwork.getId());
        repository.save(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
        logger.info(ARTWORK_EVENTS, "Artwork added successfully.");

        return artwork;
    }

    @Override
    public String saveImage(MultipartFile file) {
        return null;
    }

    @Override
    @CacheEvict(value = "artwork", key = "#id")
    public void deleteArtwork(Long id) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            repository.deleteById(id);
            logger.info(ARTWORK_EVENTS, "Artwork deleted with ID: {}", id);
        } else {
            logger.warn(ARTWORK_EVENTS, "Artwork not found for deletion with ID: {}", id);
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateArtwork(Long artworkId, ArtworkUpdate artworkUpdate) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(artworkId);
        if(optionalArtwork.isPresent()){
            ArtworkEntity artwork = optionalArtwork.get();
            artwork.setTitle(artworkUpdate.getTitle());
            artwork.setDescription(artworkUpdate.getDescription());
            artwork.setTechnique(artworkUpdate.getTechnique());
            artwork.setWidth(artworkUpdate.getWidth());
            artwork.setHeight(artworkUpdate.getHeight());
            repository.save(artwork);
            logger.info("Updating exhibition with id "+artworkId);
        }else{
            throw new NoSuchArtworkException("No artwork with id "+artworkId+" to update");
        }
        return ArtworkMapper.INSTANCE.artworkEntityToArtwork(repository.findById(artworkId).get());

    }

    @Override
    @Cacheable(cacheNames="artwork", key="#id")
    public Artwork findArtworkById(Long id) {
        Optional<ArtworkEntity> artwork = repository.findById(id);
        logger.info("Finding artwork by id (without CACHE)");
        if (artwork.isPresent())
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(artwork.get());
        else
           throw new NoSuchArtworkException("Artwork with id " + id + " not found");
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateTitle(Long id, String title) {
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
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }

    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateDescription(Long id, String description) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setDescription(description);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Description updated for artwork with ID: {}", id);
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateTechnique(Long id, String technique) {

        ThreadContext.put("artwork_id", id.toString());
        ArtworkValidator.validateTechnique(technique);
        ThreadContext.clearAll();

        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setTechnique(technique);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Technique updated for artwork with ID: {}", id);
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new  NoSuchArtworkException("Artwork not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateWidth(Long id, double width) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setWidth(width);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Width updated for artwork with ID: {}", id);
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new NoSuchArtworkException("Artwork not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateHeight(Long id, double height) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setHeight(height);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Height updated for artwork with ID: {}", id);
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new NoSuchArtworkException("Artwork not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateImgUrl(Long id, String url) {
        ThreadContext.put("artwork_id", id.toString());
        ArtworkValidator.validateImageURL(url);
        ThreadContext.clearAll();


        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setImageURL(url);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Image URL updated for artwork with ID: {}", id);
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new NoSuchArtworkException("Artwork not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="artwork", key="#id")
    public Artwork updateImgSize(Long id, double size) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.setImageSize(size);
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS, "Image Size updated for artwork with ID: {}", id);
            return ArtworkMapper.INSTANCE.artworkEntityToArtwork(existingArtwork);
        } else {
            throw new NoSuchArtworkException("Artwork not found with ID: " + id);
        }
    }


    @Override
    public List<Artwork> getArtworksByTitle(String title) {
        List<Artwork> allArt = getAllArtwork();
        List<Artwork> answer = new ArrayList<>();

        allArt.forEach(artwork -> {
            if (artwork.getTitle().equals(title))
                answer.add(artwork);
        });
        return answer;
    }

    @Override
    public List<Rating> getAllRating(Long id) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(id);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();

            List<RatingEntity> ratingEntities = existingArtwork.getRatings().stream().toList();

            return ratingEntities.stream().map(x -> RatingMapper.INSTANCE.ratingEntityToRating(x)).collect(Collectors.toList());

        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + id);
        }
    }

    @Override
    public void addRating(Long artworkId,Rating rating) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(artworkId);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtwork = optionalArtwork.get();
            existingArtwork.getRatings().add(RatingMapper.INSTANCE.ratingToRatingEntity(rating));
            repository.save(existingArtwork);
            logger.info(ARTWORK_EVENTS,"Added ratting with ID {} to artwork with ID: {}", rating.getId(), artworkId);
        } else {
            logger.warn(ARTWORK_EVENTS,"ArtworkEntity not found for adding rating with ID: {} to artwork with ID: {}", rating.getId(), artworkId);
            throw new IllegalArgumentException("ArtworkEntity not found with ID: " + artworkId);
        }
    }

    @Override
    public void deleteRating(Long artworkId,Rating rating) {
        Optional<ArtworkEntity> optionalArtwork = repository.findById(artworkId);

        if (optionalArtwork.isPresent()) {
            ArtworkEntity existingArtworkEntity = optionalArtwork.get();
            existingArtworkEntity.getRatings().remove(RatingMapper.INSTANCE.ratingToRatingEntity(rating));
            repository.save(existingArtworkEntity);
            logger.info(ARTWORK_EVENTS,"Removed rating with ID {} from artwork with ID: {}", rating.getId(), artworkId);
        } else {
            logger.warn(ARTWORK_EVENTS,"Artwork not found for removing rating with ID: {} from artwork with ID: {}", rating.getId(), artworkId);
            throw new NoSuchArtworkException("Artwork not found with ID: " + artworkId);
        }
    }


}
