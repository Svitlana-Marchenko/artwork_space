package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;

import java.util.Date;
import java.util.List;

public interface ExhibitionService{
    Exhibition createExhibition(Exhibition exhibition);
    Exhibition addToExhibition(Long id, Artwork artwork);
    Exhibition changeDates(Long id, Date startDate, Date endDate);
    Exhibition updateExhibition(ExhibitionUpdate exhibitionUpdate);
    Exhibition deleteFromExhibition(Long id, Artwork artwork);
    Exhibition editName(Long id, String newName);
    Exhibition editDescription(Long id, String newDescription);
    Exhibition findById(Long id);
    void deleteExhibition(Long id);
    void cleanupExpiredExhibitions();
    List<Exhibition> getAllActiveExhibition();
    List<Exhibition> getAllExhibitionsByCuratorId(Long id);
}
