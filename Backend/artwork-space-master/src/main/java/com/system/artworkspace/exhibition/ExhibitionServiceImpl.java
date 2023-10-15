package com.system.artworkspace.exhibition;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.system.artworkspace.logger.LoggingMarkers.EXHIBITION_EVENTS;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private ExhibitionRepository exhibitionRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Value("${exhibition.max-size}")
    private int maxSize;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }
    @Override
    public ExhibitionDto createExhibition(ExhibitionDto exhibition) {
        exhibitionRepository.save(exhibition.convertToExhibition());
        logger.info(EXHIBITION_EVENTS,"Created exhibition with ID: {}", exhibition.getId());
        return exhibition;
    }

    @Override
    public void addToExhibition(ExhibitionDto exhibition, ArtworkDto artwork) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            if (existingExhibition.getArtworks().size() < maxSize) {
                existingExhibition.getArtworks().add(artwork.convertToArtwork());
                exhibitionRepository.save(existingExhibition);
                logger.info(EXHIBITION_EVENTS,"Added artwork with ID {} to exhibition with ID: {}", artwork.getId(), exhibition.getId());
            } else {
                throw new IllegalArgumentException("Exhibition size exceeds the maximum allowed.");
            }
        } else {
            logger.warn(EXHIBITION_EVENTS,"Exhibition not found for adding artwork with ID: {} to exhibition with ID: {}", artwork.getId(), exhibition.getId());
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void changeDates(ExhibitionDto exhibition, Date startDate, Date endDate) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.setStartDate(startDate);
            existingExhibition.setEndDate(endDate);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Changed exhibition dates for exhibition with ID: {}", exhibition.getId());
        } else {
            logger.warn(EXHIBITION_EVENTS,"Exhibition not found for changing dates with ID: {}", exhibition.getId());
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void deleteFromExhibition(ExhibitionDto exhibition, ArtworkDto artwork) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.getArtworks().remove(artwork.convertToArtwork());
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Removed artwork with ID {} from exhibition with ID: {}", artwork.getId(), exhibition.getId());
        } else {
            logger.warn(EXHIBITION_EVENTS,"Exhibition not found for removing artwork with ID: {} from exhibition with ID: {}", artwork.getId(), exhibition.getId());
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void editName(ExhibitionDto exhibition, String newName) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.setName(newName);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Edited exhibition name for exhibition with ID: {}", exhibition.getId());
        } else {
            logger.warn(EXHIBITION_EVENTS,"Exhibition not found for editing name with ID: {}", exhibition.getId());
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void editDescription(ExhibitionDto exhibition, String newDescription) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.setDescription(newDescription);
            exhibitionRepository.save(existingExhibition);
            logger.info(EXHIBITION_EVENTS,"Edited exhibition description for exhibition with ID: {}", exhibition.getId());
        } else {
            logger.warn(EXHIBITION_EVENTS,"Exhibition not found for editing description with ID: {}", exhibition.getId());
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public ExhibitionDto findById(Long id) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(id);
        return optionalExhibition.orElse(null).convertToExhibitionDto();
    }

    @Override
    public void deleteExhibition(ExhibitionDto exhibition) {
        exhibitionRepository.delete(exhibition.convertToExhibition());
        logger.info(EXHIBITION_EVENTS,"Deleted exhibition with ID: {}", exhibition.getId());
    }
}
