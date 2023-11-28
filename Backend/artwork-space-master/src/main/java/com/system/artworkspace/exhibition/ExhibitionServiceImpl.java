package com.system.artworkspace.exhibition;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkMapper;
import jakarta.persistence.EntityNotFoundException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.COLLECTION_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.EXHIBITION_EVENTS;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private ExhibitionRepository exhibitionRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Value("${exhibition.max-size}")
    private int maxSize;

    private final Scheduler scheduler;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository, Scheduler scheduler) throws SchedulerException {
        this.exhibitionRepository = exhibitionRepository;
        this.scheduler = scheduler;
        this.scheduler.start();
    }
    @Override
    public Exhibition createExhibition(Exhibition exhibition) {
        exhibitionRepository.save(ExhibitionMapper.INSTANCE.exhibitionToExhibitionEntity(exhibition));
        logger.info(EXHIBITION_EVENTS,"Created exhibition with ID: {}", exhibition.getId());
        return exhibition;
    }

    @Override
    public void addToExhibition(Long id, Artwork artwork) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            if (existingExhibition.getArtworks().size() < maxSize) {
                existingExhibition.getArtworks().add(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
                exhibitionRepository.save(existingExhibition);
                logger.info(EXHIBITION_EVENTS,"Added artwork with ID {} to exhibition with ID: {}", artwork.getId(), id);
            } else {
                throw new IllegalArgumentException("ExhibitionEntity size exceeds the maximum allowed.");
            }
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for adding artwork with ID: {} to exhibition with ID: {}", artwork.getId(), id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    public void changeDates(Long id, Date startDate, Date endDate) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            existingExhibition.setStartDate(startDate);
            existingExhibition.setEndDate(endDate);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Changed exhibition dates for exhibition with ID: {}", id);
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for changing dates with ID: {}", id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    public void deleteFromExhibition(Long id, Artwork artwork) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            existingExhibition.getArtworks().remove(ArtworkMapper.INSTANCE.artworkToArtworkEntity(artwork));
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Removed artwork with ID {} from exhibition with ID: {}", artwork.getId(), id);
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for removing artwork with ID: {} from exhibition with ID: {}", artwork.getId(), id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    public void editName(Long id, String newName) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            existingExhibition.setTitle(newName);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Edited exhibition name for exhibition with ID: {}", id);
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for editing name with ID: {}", id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    public void editDescription(Long id, String newDescription) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);

        if (optionalExhibition.isPresent()) {
            ExhibitionEntity existingExhibition = optionalExhibition.get();
            existingExhibition.setDescription(newDescription);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Edited exhibition description for exhibition with ID: {}", id);
        } else {
            logger.warn(EXHIBITION_EVENTS,"ExhibitionEntity not found for editing description with ID: {}", id);
            throw new IllegalArgumentException("ExhibitionEntity not found with ID: " + id);
        }
    }

    @Override
    public Exhibition findById(Long id) {
        Optional<ExhibitionEntity> optionalExhibition = exhibitionRepository.findById(id);
        return ExhibitionMapper.INSTANCE.exhibitionEntityToExhibition(optionalExhibition.orElse(null));
    }

    @Override
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
    @Scheduled(cron = "0 0 2 1 * ?") // Execute on the 1st day of every month at 2:00 AM
    public void cleanupExpiredExhibitionsMonthly() {
        cleanupExpiredExhibitions();
        logger.info("Cleanup Expired Exhibitions Monthly task executed");
    }

    @Override
    public void cleanupExpiredExhibitions() {

        boolean hasExhibitions = exhibitionRepository.count() > 0;
        Date threeMonthsAgo = Date.from(LocalDateTime.now().minusMonths(3).atZone(ZoneId.systemDefault()).toInstant());
        List<ExhibitionEntity> expiredExhibitions = exhibitionRepository.findByEndDateBefore(threeMonthsAgo);
        List<ExhibitionEntity> exhibitions = exhibitionRepository.findAll();

        for (ExhibitionEntity exhibition : expiredExhibitions) {
            deleteExhibition(exhibition.getId());
            logger.info("Cleaned up exhibition with ID: " + exhibition.getId());
        }

        logger.info("Cleanup Expired Exhibitions task executed...");
    }


}
