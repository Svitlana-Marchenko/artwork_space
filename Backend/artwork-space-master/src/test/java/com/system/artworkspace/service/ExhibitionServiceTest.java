package com.system.artworkspace.service;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import com.system.artworkspace.exhibition.*;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;
import com.system.artworkspace.utils.ArtworkUtils;
import com.system.artworkspace.utils.ExhibitionUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExhibitionServiceTest {

    @Mock
    private ExhibitionRepository exhibitionRepository;

    @InjectMocks
    private ExhibitionServiceImpl exhibitionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateExhibition_whenValidExhibitionProvided() {
        Exhibition exhibition = ExhibitionUtils.getRandomExhibition();
        exhibition.setId(1L);

        ExhibitionEntity exhibitionEntity = ExhibitionUtils.getRandomExhibitionEntity();
        exhibitionEntity.setId(1L);

        when(exhibitionRepository.save(any(ExhibitionEntity.class))).thenReturn(exhibitionEntity);

        Exhibition createdExhibition = exhibitionService.createExhibition(exhibition);

        assertNotNull(createdExhibition);
        assertEquals(exhibition.getId(), createdExhibition.getId());
        verify(exhibitionRepository, times(1)).save(any(ExhibitionEntity.class));
    }

    @Test
    public void shouldAddToExhibition_whenValidArtworkProvided() {
        Long exhibitionId = 1L;
        Artwork artwork = ArtworkUtils.getRandomArtwork();
        artwork.setId(2L);

        ExhibitionEntity exhibitionEntity = ExhibitionUtils.getRandomExhibitionEntity();
        exhibitionEntity.setId(exhibitionId);

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.of(exhibitionEntity));

        Exhibition updatedExhibition = exhibitionService.addToExhibition(exhibitionId, artwork);

        assertNotNull(updatedExhibition);
        verify(exhibitionRepository, times(1)).save(any(ExhibitionEntity.class));
    }

    @Test
    public void shouldChangeDates_whenValidDatesProvided() {
        Long exhibitionId = 1L;
        Date startDate = new Date();
        Date endDate = new Date();

        ExhibitionEntity exhibitionEntity = ExhibitionUtils.getRandomExhibitionEntity();
        exhibitionEntity.setId(exhibitionId);

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.of(exhibitionEntity));

        Exhibition updatedExhibition = exhibitionService.changeDates(exhibitionId, startDate, endDate);

        assertNotNull(updatedExhibition);
        assertEquals(startDate, exhibitionEntity.getStartDate());
        assertEquals(endDate, exhibitionEntity.getEndDate());
        verify(exhibitionRepository, times(1)).save(exhibitionEntity);
    }

    @Test
    public void shouldThrowException_whenExhibitionNotFoundForChangeDates() {
        Long exhibitionId = 1L;
        Date startDate = new Date();
        Date endDate = new Date();

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> exhibitionService.changeDates(exhibitionId, startDate, endDate));
    }

    @Test
    public void shouldUpdateExhibition_whenValidExhibitionUpdateProvided() {
        Long exhibitionId = 1L;
        ExhibitionUpdate exhibitionUpdate = ExhibitionUtils.getRandomExhibitionUpdate();
        exhibitionUpdate.setId(exhibitionId);
        exhibitionUpdate.setTitle("New Title");

        ExhibitionEntity exhibitionEntity = new ExhibitionEntity();
        exhibitionEntity.setId(exhibitionId);

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.of(exhibitionEntity));

        Exhibition updatedExhibition = exhibitionService.updateExhibition(exhibitionUpdate);

        assertEquals(exhibitionUpdate.getTitle(), exhibitionEntity.getTitle());
        verify(exhibitionRepository, times(1)).save(exhibitionEntity);
    }

    @Test
    public void shouldThrowException_whenExhibitionNotFoundForUpdate() {
        long exhibitionId = 1L;
        ExhibitionUpdate exhibitionUpdate = ExhibitionUtils.getRandomExhibitionUpdate();
        exhibitionUpdate.setId(exhibitionId);

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.empty());

        assertThrows(NoSuchExhibitionException.class, () -> exhibitionService.updateExhibition(exhibitionUpdate));
    }

    @Test
    public void shouldDeleteExhibition_whenValidExhibitionIdProvided() {
        Long exhibitionId = 1L;
        ExhibitionEntity exhibitionEntity = ExhibitionUtils.getRandomExhibitionEntity();
        exhibitionEntity.setId(exhibitionId);

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.of(exhibitionEntity));

        exhibitionService.deleteExhibition(exhibitionId);

        verify(exhibitionRepository, times(1)).delete(exhibitionEntity);
    }

    @Test
    public void shouldThrowException_whenExhibitionNotFoundForDelete() {
        Long exhibitionId = 1L;

        when(exhibitionRepository.findById(exhibitionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> exhibitionService.deleteExhibition(exhibitionId));
    }
}
