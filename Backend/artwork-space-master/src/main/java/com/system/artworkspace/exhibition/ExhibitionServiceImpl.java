package com.system.artworkspace.exhibition;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;
import jakarta.persistence.EntityNotFoundException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.*;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private ExhibitionRepository exhibitionRepository;

    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Value("${exhibition.max-size}")
    private int maxSize;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository){
        this.exhibitionRepository = exhibitionRepository;
    }

    @Override
    public Exhibition createExhibition(Exhibition exhibition) {
        ExhibitionEntity ex = exhibitionRepository.save(ExhibitionMapper.INSTANCE.exhibitionToExhibitionEntity(exhibition));
        logger.info(EXHIBITION_EVENTS,"Created exhibition with ID: {}", ex.getId());
        return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(ex);
    }

    @Override
    @CachePut(cacheNames="exhibition", key="#id")
    public Exhibition addToExhibition(Long id, Artwork artwork) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            if (existingExhibition.getArtworks().size() < maxSize) {
                existingExhibition.getArtworks().add(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
                exhibitionRepository.save(existingExhibition);
                logger.info(EXHIBITION_EVENTS,"Added artwork with ID {} to exhibition with ID: {}", artwork.getId(), id);
                return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(existingExhibition);
            } else {
                throw new IllegalArgumentException("ExhibitionEntity size exceeds the maximum allowed.");
            }
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for adding artwork with ID: {} to exhibition with ID: {}", artwork.getId(), id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="exhibition", key="#id")
    public Exhibition changeDates(Long id, Date startDate, Date endDate) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            existingExhibition.setStartDate(startDate);
            existingExhibition.setEndDate(endDate);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Changed exhibition dates for exhibition with ID: {}", id);
            return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(existingExhibition);
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for changing dates with ID: {}", id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    @CachePut(cacheNames="exhibition", key="#exhibitionUpdate.id")
    public Exhibition updateExhibition(ExhibitionUpdate exhibitionUpdate) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(exhibitionUpdate.getId());
        if(optionalExhibition.isPresent()){
            ExhibitionEntity exhibition = optionalExhibition.get();
            exhibition.setTitle(exhibitionUpdate.getTitle());
            exhibition.setDescription(exhibitionUpdate.getDescription());
            exhibitionRepository.save(exhibition);
            logger.info("Updating exhibition with id "+exhibitionUpdate.getId());
        }else{
            throw new NoSuchExhibitionException("No exhibition with id "+exhibitionUpdate.getId()+" to update");
        }
        return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(exhibitionRepository.findById(exhibitionUpdate.getId()).get());
    }

    @Override
    @CachePut(cacheNames="exhibition", key="#id")
    public Exhibition deleteFromExhibition(Long id, Artwork artwork) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            existingExhibition.getArtworks().remove(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Removed artwork with ID {} from exhibition with ID: {}", artwork.getId(), id);
            return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(existingExhibition);
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for removing artwork with ID: {} from exhibition with ID: {}", artwork.getId(), id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    @Cacheable(cacheNames="exhibition", key="#id")
    public Exhibition findById(Long id) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);
        logger.debug("Finding exhibition by id (without CACHE)");
        return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(optionalExhibition.orElse(null));
    }

    @Override
    @CacheEvict(value = "exhibition", key = "#id")
    public void deleteExhibition(Long id) {
        Optional<ExhibitionEntity> optionalCollection = exhibitionRepository.findById(id);

        if (optionalCollection.isPresent()) {
            ExhibitionEntity existingExhibitionEntity = optionalCollection.get();
            exhibitionRepository.delete(existingExhibitionEntity);
            logger.info(COLLECTION_EVENTS,"Deleted exhibition with ID: {}", id);
        } else {
            logger.warn(COLLECTION_EVENTS,"Exhibition not found for deletion with ID: {}", id);
            throw new EntityNotFoundException("Exhibition not found with ID: " + id);
        }
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Execute every 24 hours
    @CacheEvict(value = "exhibition",allEntries = true)
    public void deleteCache(){
        logger.info("Cache deleted for all exhibitions");
    }

    @Scheduled(cron = "0 0 2 1 * ?") // Execute on the 1st day of every month at 2:00 AM
    public void cleanupExpiredExhibitionsMonthly() {
        cleanupExpiredExhibitions();
        logger.info("Cleanup Expired Exhibitions Monthly task executed");
    }

    @Override
    public void cleanupExpiredExhibitions() {
        Date threeMonthsAgo = Date.from(LocalDateTime.now().minusMonths(3).atZone(ZoneId.systemDefault()).toInstant());
        List<ExhibitionEntity> expiredExhibitions = exhibitionRepository.findByEndDateBefore(threeMonthsAgo);

        for (ExhibitionEntity exhibition : expiredExhibitions) {
            deleteExhibition(exhibition.getId());
            logger.info("Cleaned up exhibition with ID: " + exhibition.getId());
        }

        logger.info("Cleanup Expired Exhibitions task executed...");
    }

    @Override
    public List<Exhibition> getAllActiveExhibition() {
        Sort sort = Sort.by(Sort.Order.asc("startDate"));
        List<ExhibitionEntity> activeExhibitionEntities = exhibitionRepository.findAll(sort);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCurrentDate = dateFormat.format(new Date());

        List<ExhibitionEntity> validExhibitionEntities = activeExhibitionEntities.stream()
                .filter(entity -> {
                    try {
                        Date closingTime = dateFormat.parse(entity.getEndDate().toString());
                        Date startTime = dateFormat.parse(entity.getStartDate().toString());
                        return !closingTime.before(dateFormat.parse(formattedCurrentDate)) && ! startTime.after(dateFormat.parse(formattedCurrentDate));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        logger.debug(AUCTIONS_EVENTS, "Retrieved {} active exhibitions.", validExhibitionEntities.size());

        return validExhibitionEntities.stream()
                .map(ExhibitionMapper.INSTANCE::exhibitionEntityToExhibition)
                .collect(Collectors.toList());
    }

    @Override
    public List<Exhibition> getAllExhibitionsByCuratorId(Long id) {
        logger.debug("Getting exhibition with curator ID: " + id);
        return exhibitionRepository.findByCuratorId(id).stream()
                .map(ExhibitionMapper.INSTANCE::exhibitionEntityToExhibition)
                .collect(Collectors.toList());
    }
}
