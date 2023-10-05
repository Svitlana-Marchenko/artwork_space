package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private ExhibitionRepository exhibitionRepository;

    @Value("${exhibition.max-size}")
    private int maxSize;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }


    @Override
    public Exhibition createExhibition(User user, String name, String description, Date startDate, Date endDate, List<Artwork> artworks) {
        if (artworks.size() > maxSize) {
            throw new IllegalArgumentException("Exhibition size exceeds the maximum allowed.");
        }

        Exhibition exhibition = new Exhibition(user, name, description, artworks, startDate, endDate);
        return exhibitionRepository.save(exhibition);
    }

    @Override
    public void addToExhibition(Exhibition exhibition, Artwork artwork) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            if (existingExhibition.getArtworks().size() < maxSize) {
                existingExhibition.getArtworks().add(artwork);
                exhibitionRepository.save(existingExhibition);
            } else {
                throw new IllegalArgumentException("Exhibition size exceeds the maximum allowed.");
            }
        } else {
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void changeDates(Exhibition exhibition, Date startDate, Date endDate) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.setStartDate(startDate);
            existingExhibition.setEndDate(endDate);
            exhibitionRepository.save(existingExhibition);
        } else {
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void deleteFromExhibition(Exhibition exhibition, Artwork artwork) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.getArtworks().remove(artwork);
            exhibitionRepository.save(existingExhibition);
        } else {
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void editName(Exhibition exhibition, String newName) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.setName(newName);
            exhibitionRepository.save(existingExhibition);
        } else {
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public void editDescription(Exhibition exhibition, String newDescription) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(exhibition.getId());

        if (optionalExhibition.isPresent()) {
            Exhibition existingExhibition = optionalExhibition.get();
            existingExhibition.setDescription(newDescription);
            exhibitionRepository.save(existingExhibition);
        } else {
            throw new IllegalArgumentException("Exhibition not found with ID: " + exhibition.getId());
        }
    }

    @Override
    public Exhibition findById(Long id) {
        Optional<Exhibition> optionalExhibition = exhibitionRepository.findById(id);
        return optionalExhibition.orElse(null);
    }

    @Override
    public void deleteExhibition(Exhibition exhibition) {
        exhibitionRepository.delete(exhibition);
    }
}
