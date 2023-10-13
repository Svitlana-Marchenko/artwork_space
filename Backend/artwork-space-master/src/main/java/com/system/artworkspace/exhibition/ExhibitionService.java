package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.user.User;

import java.util.Date;
import java.util.List;

public interface ExhibitionService {
    ExhibitionDto createExhibition(User owner, String name, String description, Date startDate, Date endDate, List<ArtworkDto> artworks);
    void addToExhibition(ExhibitionDto exhibition, ArtworkDto artwork);
    void changeDates(ExhibitionDto exhibition, Date startDate, Date endDate);
    void deleteFromExhibition(ExhibitionDto exhibition, ArtworkDto artwork);
    void editName(ExhibitionDto exhibition, String newName);
    void editDescription(ExhibitionDto exhibition, String newDescription);
    ExhibitionDto findById(Long id);
    void deleteExhibition(ExhibitionDto exhibition);
}
