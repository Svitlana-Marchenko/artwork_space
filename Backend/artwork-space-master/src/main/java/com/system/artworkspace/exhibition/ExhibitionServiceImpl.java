package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ExhibitionServiceImpl implements ExhibitionService{

    @Value("${exhibition.max-size}")
    private int maxSize;

    @Override
    public Exhibition createExhibition(String name, String description, Date startDate, Date endDate, List<Artwork> artworks) {
        return null;
    }

    @Override
    public void addToExhibition(Exhibition exhibition, Artwork artwork) {
        if(exhibition.getArtworks().size()<maxSize){
            //--
        }
    }

    @Override
    public void changeDates(Exhibition exhibition, Date startDate, Date endDate) {

    }

    @Override
    public void deleteFromExhibition(Exhibition exhibition, Artwork artwork) {

    }

    @Override
    public void editName(Exhibition exhibition, String newName) {

    }

    @Override
    public void editDescription(Exhibition exhibition, String newDescription) {

    }
}
