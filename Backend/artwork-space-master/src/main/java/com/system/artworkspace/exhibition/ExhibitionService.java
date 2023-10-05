package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.user.User;

import java.util.Date;
import java.util.List;

public interface ExhibitionService {
    Exhibition createExhibition(User owner, String name, String description, Date startDate, Date endDate, List<Artwork> artworks);
    void addToExhibition(Exhibition exhibition, Artwork artwork);
    void changeDates(Exhibition exhibition, Date startDate, Date endDate);
    void deleteFromExhibition(Exhibition exhibition, Artwork artwork);
    void editName(Exhibition exhibition, String newName);
    void editDescription(Exhibition exhibition, String newDescription);
    Exhibition findById(Long id);
    void deleteExhibition(Exhibition exhibition);
}
