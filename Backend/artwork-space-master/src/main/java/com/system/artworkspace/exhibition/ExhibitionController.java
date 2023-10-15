package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import com.system.artworkspace.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ExhibitionDto createExhibition(
        @RequestBody @Valid ExhibitionDto exhibition,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Creating an exhibition with name: {}", exhibition.getName());
        ExhibitionDto createdExhibition = exhibitionService.createExhibition(exhibition);
        logger.info("Exhibition created with ID: {}", createdExhibition.getId());
        return createdExhibition;
    }

    @PostMapping("/{exhibitionId}/addArtwork")
    public void addToExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ArtworkDto artwork,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Adding artwork with ID {} to exhibition with ID: {}", artwork.getId(), exhibitionId);
        //exhibitionService.addToExhibition(exhibitionId, artwork);
        logger.info("Artwork added to exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/changeDates")
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
        //exhibitionService.changeDates(exhibitionId, startDate, endDate);
        logger.info("Dates changed for exhibition with ID: {}", exhibitionId);
    }

    @DeleteMapping("/{exhibitionId}/removeArtwork")
    public void deleteFromExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody ArtworkDto artwork
    ) {
        logger.info("Removing artwork with ID {} from exhibition with ID: {}", artwork.getId(), exhibitionId);
        //exhibitionService.deleteFromExhibition(exhibitionId, artwork);
        logger.info("Artwork removed from exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editName")
    public void editName(
            @PathVariable Long exhibitionId,
            @RequestParam String name
    ) {
        logger.info("Updating exhibition name for exhibition with ID: {}", exhibitionId);
        //exhibitionService.editName(exhibitionId, name);
        logger.info("Exhibition name updated for exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editDescription")
    public void editDescription(
            @PathVariable Long exhibitionId,
            @RequestParam String description
    ) {
        logger.info("Updating exhibition description for exhibition with ID: {}", exhibitionId);
        //exhibitionService.editDescription(exhibitionId, description);
        logger.info("Exhibition description updated for exhibition with ID: {}", exhibitionId);
    }

    @GetMapping("/{exhibitionId}")
    public ExhibitionDto findById(@PathVariable @Valid Long exhibitionId) {
        logger.info("Retrieving exhibition with ID: {}", exhibitionId);
        return exhibitionService.findById(exhibitionId);
    }

    @DeleteMapping("/{exhibitionId}")
    public void deleteExhibition(@PathVariable Long exhibitionId) {
        logger.info("Deleting exhibition with ID: {}", exhibitionId);
        //exhibitionService.deleteExhibition(exhibitionId);
        logger.info("Exhibition deleted with ID: {}", exhibitionId);
    }

    @ExceptionHandler(NoSuchExhibitionException.class)
    public ResponseEntity<String> handleNoSuchExhibitionException(NoSuchExhibitionException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exhibition not found: " + e.getMessage());
    }

    private void logErrors (BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError o : allErrors){
            logger.info("error -->  " + o.getDefaultMessage());
        }
    }
}
