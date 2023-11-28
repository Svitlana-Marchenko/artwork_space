package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;

import java.util.Date;

public interface ExhibitionService {
    Exhibition createExhibition(Exhibition exhibition);
    void addToExhibition(Long id, Artwork artwork);
    void changeDates(Long id, Date startDate, Date endDate);
    void deleteFromExhibition(Long id, Artwork artwork);
    void editName(Long id, String newName);
    void editDescription(Long id, String newDescription);
    Exhibition findById(Long id);
    void deleteExhibition(Long id);
    void cleanupExpiredExhibitions();
}
