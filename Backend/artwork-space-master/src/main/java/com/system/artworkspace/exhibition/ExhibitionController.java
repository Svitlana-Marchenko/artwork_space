package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import com.system.artworkspace.exceptions.ValidationException;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateDto;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateMapper;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exhibitions")
@Slf4j
public class ExhibitionController {

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
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ExhibitionDto createdExhibition = ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionDtoToExhibition(exhibition)));
        log.info("Exhibition created with ID: {}", createdExhibition.getId());
        return createdExhibition;
    }

    @PutMapping
    public ExhibitionDto updateExhibition(
            @RequestBody @Valid ExhibitionUpdateDto exhibitionUpdate,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ExhibitionDto exhibitionN = ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.updateExhibition(ExhibitionUpdateMapper.INSTANCE.exhibitionUpdateDtoToExhibitionUpdate(exhibitionUpdate)));
        log.info("Exhibition updated with ID: {}", exhibitionN.getId());
        return exhibitionN;
    }

    @PostMapping("/{exhibitionId}/addArtwork")
    public void addToExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ArtworkDto artwork,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        log.info("Adding artwork with ID {} to exhibition with ID: {}", artwork.getId(), exhibitionId);
        exhibitionService.addToExhibition(exhibitionId, ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork));
        log.info("Artwork added to exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/changeDates")
    public void changeDates(
            @PathVariable Long exhibitionId,
            @RequestParam @Valid Date startDate,
            @RequestParam @Valid Date endDate,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        log.info("Changing dates for exhibition with ID: {}", exhibitionId);
        exhibitionService.changeDates(exhibitionId, startDate, endDate);
        log.info("Dates changed for exhibition with ID: {}", exhibitionId);
    }

    @DeleteMapping("/{exhibitionId}/removeArtwork")
    public void deleteFromExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ArtworkDto artwork, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        log.info("Removing artwork with ID {} from exhibition with ID: {}", artwork.getId(), exhibitionId);
        exhibitionService.deleteFromExhibition(exhibitionId, ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork));
        log.info("Artwork removed from exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editName")
    public void editName(
            @PathVariable Long exhibitionId,
            @RequestParam String name
    ) {
        log.info("Updating exhibition name for exhibition with ID: {}", exhibitionId);
        exhibitionService.editName(exhibitionId, name);
        log.info("ExhibitionEntity name updated for exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editDescription")
    public void editDescription(
            @PathVariable Long exhibitionId,
            @RequestParam String description
    ) {
        log.info("Updating exhibition description for exhibition with ID: {}", exhibitionId);
        exhibitionService.editDescription(exhibitionId, description);
        log.info("ExhibitionEntity description updated for exhibition with ID: {}", exhibitionId);
    }

    @GetMapping("/{exhibitionId}")
    public ExhibitionDto findById(@PathVariable @Valid Long exhibitionId) {
        log.debug("Retrieving exhibitions with curator ID: {}", exhibitionId);
        return ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.findById(exhibitionId));
    }

    @GetMapping("curator/{curatorId}")
    public List<ExhibitionDto> findByCuratorId(@PathVariable @Valid Long curatorId) {
        log.debug("Retrieving exhibition with ID: {}", curatorId);
        return exhibitionService.getAllExhibitionsByCuratorId(curatorId).stream().map(ExhibitionMapper.INSTANCE::exhibitionToExhibitionDto).collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<ExhibitionDto> getAllActive() {
        log.debug("Fetching all active auctions");
        List<ExhibitionDto> activeExhibitions = exhibitionService.getAllActiveExhibition().stream().map(ExhibitionMapper.INSTANCE::exhibitionToExhibitionDto).collect(Collectors.toList());
        log.debug("Retrieving info about active exhibitions");
        return activeExhibitions;
    }

    @DeleteMapping("/{exhibitionId}")
    public void deleteExhibition(@PathVariable Long exhibitionId) {
        log.info("Deleting exhibition with ID: {}", exhibitionId);
        exhibitionService.deleteExhibition(exhibitionId);
        log.info("ExhibitionEntity deleted with ID: {}", exhibitionId);
    }

    @ExceptionHandler(NoSuchExhibitionException.class)
    public ResponseEntity<String> handleNoSuchExhibitionException(NoSuchExhibitionException e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ExhibitionEntity not found: " + e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
