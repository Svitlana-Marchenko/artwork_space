package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/exhibitions")
public class ExhibitionController {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitionController.class);

    private final ExhibitionService exhibitionService;

    @Autowired
    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CURATOR')")
    public ExhibitionDto createExhibition(
        @RequestBody @Valid ExhibitionDto exhibition,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Creating an exhibition with name: {}", exhibition.getName());
        ExhibitionDto createdExhibition = ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionDtoToExhibition(exhibition)));
        logger.info("Exhibition created with ID: {}", createdExhibition.getId());
        return createdExhibition;
    }

    @PostMapping("/{exhibitionId}/addArtwork")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void addToExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ArtworkDto artwork,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Adding artwork with ID {} to exhibition with ID: {}", artwork.getId(), exhibitionId);
        exhibitionService.addToExhibition(exhibitionId, ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork));
        logger.info("Artwork added to exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/changeDates")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void changeDates(
            @PathVariable Long exhibitionId,
            @RequestParam @Valid Date startDate,
            @RequestParam @Valid Date endDate,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Changing dates for exhibition with ID: {}", exhibitionId);
        exhibitionService.changeDates(exhibitionId, startDate, endDate);
        logger.info("Dates changed for exhibition with ID: {}", exhibitionId);
    }

    @DeleteMapping("/{exhibitionId}/removeArtwork")
    @PreAuthorize("hasRole('CURATOR')")
    public void deleteFromExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody ArtworkDto artwork
    ) {
        logger.info("Removing artwork with ID {} from exhibition with ID: {}", artwork.getId(), exhibitionId);
        exhibitionService.deleteFromExhibition(exhibitionId, ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork));
        logger.info("Artwork removed from exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editName")
    @PreAuthorize("hasRole('CURATOR')")
    public void editName(
            @PathVariable Long exhibitionId,
            @RequestParam String name
    ) {
        logger.info("Updating exhibition name for exhibition with ID: {}", exhibitionId);
        //exhibitionService.editName(exhibitionId, name);
        logger.info("ExhibitionEntity name updated for exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editDescription")
    @PreAuthorize("hasRole('CURATOR')")
    public void editDescription(
            @PathVariable Long exhibitionId,
            @RequestParam String description
    ) {
        logger.info("Updating exhibition description for exhibition with ID: {}", exhibitionId);
        exhibitionService.editDescription(exhibitionId, description);
        logger.info("ExhibitionEntity description updated for exhibition with ID: {}", exhibitionId);
    }

    @GetMapping("/{exhibitionId}")
    public ExhibitionDto findById(@PathVariable @Valid Long exhibitionId) {
        logger.info("Retrieving exhibition with ID: {}", exhibitionId);
        return ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.findById(exhibitionId));
    }

    @DeleteMapping("/{exhibitionId}")
    @PreAuthorize("hasRole('CURATOR')")
    public void deleteExhibition(@PathVariable Long exhibitionId) {
        logger.info("Deleting exhibition with ID: {}", exhibitionId);
        exhibitionService.deleteExhibition(exhibitionId);
        logger.info("ExhibitionEntity deleted with ID: {}", exhibitionId);
    }

    @ExceptionHandler(NoSuchExhibitionException.class)
    public ResponseEntity<String> handleNoSuchExhibitionException(NoSuchExhibitionException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ExhibitionEntity not found: " + e.getMessage());
    }

    private void logErrors (BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError o : allErrors){
            logger.info("error -->  " + o.getDefaultMessage());
        }
    }
}
