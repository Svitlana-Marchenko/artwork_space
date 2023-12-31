package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkDto;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.auction.AuctionDto;
import com.system.artworkspace.auction.AuctionMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.NoSuchExhibitionException;
import com.system.artworkspace.exceptions.ValidationException;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateDto;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateMapper;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    //@PreAuthorize("hasAuthority('CURATOR')")
    public ExhibitionDto createExhibition(
        @RequestBody @Valid ExhibitionDto exhibition,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ExhibitionDto createdExhibition = ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.createExhibition(ExhibitionMapper.INSTANCE.exhibitionDtoToExhibition(exhibition)));
        logger.info("Exhibition created with ID: {}", createdExhibition.getId());
        return createdExhibition;
    }

    @PutMapping
    //@PreAuthorize("hasAuthority('CURATOR')")
    public ExhibitionDto updateExhibition(
            @RequestBody @Valid ExhibitionUpdateDto exhibitionUpdate,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ExhibitionDto exhibitionN = ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.updateExhibition(ExhibitionUpdateMapper.INSTANCE.exhibitionUpdateDtoToExhibitionUpdate(exhibitionUpdate)));
        logger.info("Exhibition updated with ID: {}", exhibitionN.getId());
        return exhibitionN;
    }

    @PostMapping("/{exhibitionId}/addArtwork")
    //@PreAuthorize("hasAuthority('CURATOR')")
    public void addToExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ArtworkDto artwork,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Adding artwork with ID {} to exhibition with ID: {}", artwork.getId(), exhibitionId);
        exhibitionService.addToExhibition(exhibitionId, ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork));
        logger.info("Artwork added to exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/changeDates")
    //@PreAuthorize("hasAuthority('CURATOR')")
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
        logger.info("Changing dates for exhibition with ID: {}", exhibitionId);
        exhibitionService.changeDates(exhibitionId, startDate, endDate);
        logger.info("Dates changed for exhibition with ID: {}", exhibitionId);
    }

    @DeleteMapping("/{exhibitionId}/removeArtwork")
    //@PreAuthorize("hasRole('CURATOR')")
    public void deleteFromExhibition(
            @PathVariable Long exhibitionId,
            @RequestBody @Valid ArtworkDto artwork, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Removing artwork with ID {} from exhibition with ID: {}", artwork.getId(), exhibitionId);
        exhibitionService.deleteFromExhibition(exhibitionId, ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork));
        logger.info("Artwork removed from exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editName")
    //@PreAuthorize("hasRole('CURATOR')")
    public void editName(
            @PathVariable Long exhibitionId,
            @RequestParam String name
    ) {
        logger.info("Updating exhibition name for exhibition with ID: {}", exhibitionId);
        //exhibitionService.editName(exhibitionId, name);
        logger.info("ExhibitionEntity name updated for exhibition with ID: {}", exhibitionId);
    }

    @PutMapping("/{exhibitionId}/editDescription")
    //@PreAuthorize("hasRole('CURATOR')")
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
        logger.info("Retrieving exhibitions with curator ID: {}", exhibitionId);
        return ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(exhibitionService.findById(exhibitionId));
    }

    @GetMapping("curator/{curatorId}")
    public List<ExhibitionDto> findByCuratorId(@PathVariable @Valid Long curatorId) {
        logger.info("Retrieving exhibition with ID: {}", curatorId);
        return exhibitionService.getAllExhibitionsByCuratorId(curatorId).stream().map(x -> ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(x)).collect(Collectors.toList());
    }

    @GetMapping("/active")
    public List<ExhibitionDto> getAllActive() {
        logger.info("Fetching all active auctions");
        List<ExhibitionDto> activeExhibitions = exhibitionService.getAllActiveExhibition().stream().map(x -> ExhibitionMapper.INSTANCE.exhibitionToExhibitionDto(x)).collect(Collectors.toList());
        logger.info("Retrieving info about active exhibitions ", activeExhibitions.size());
        return activeExhibitions;
    }

    @DeleteMapping("/{exhibitionId}")
    //@PreAuthorize("hasRole('CURATOR')")
    public void deleteExhibition(@PathVariable Long exhibitionId) {
        logger.info("Deleting exhibition with ID: {}", exhibitionId);
        exhibitionService.deleteExhibition(exhibitionId);
        logger.info("ExhibitionEntity deleted with ID: {}", exhibitionId);
    }

    @ExceptionHandler(NoSuchExhibitionException.class)
    public ResponseEntity<String> handleNoSuchExhibitionException(NoSuchExhibitionException e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ExhibitionEntity not found: " + e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
