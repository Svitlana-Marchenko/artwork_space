package com.system.artworkspace.artwork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateDto;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateMapper;
import com.system.artworkspace.exceptions.*;
import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingMapper;
import javax.validation.Valid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public List<ArtworkDto> getAll() {
        log.info("Getting all artworks");
        return artworkService.getAllArtwork().stream().map(ArtworkMapper.INSTANCE::artworkToArtworkDto).collect(Collectors.toList());
    }

    @GetMapping("/artist/{id}")
    public List<ArtworkDto> getAllByUserId(@PathVariable Long id) {
        log.info("Getting all artworks");
        return artworkService.getAllArtworkByArtistId(id).stream().map(ArtworkMapper.INSTANCE::artworkToArtworkDto).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ARTIST')")
    public ArtworkDto addArtwork(@RequestPart("file") MultipartFile file,
                                 @RequestPart("artwork") @Valid String artworkJson,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        try {
            ArtworkDto artwork = new ObjectMapper().readValue(artworkJson, ArtworkDto.class);
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<ArtworkDto>> violations = validator.validate(artwork);

            if (!violations.isEmpty()) {
                String message = ExceptionHelper.formErrorMessage(violations);
                throw new javax.validation.ValidationException(message);
            } else {
                ArtworkDto addedArtwork = ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork), file));
                log.info("Artwork added successfully with ID: {}", addedArtwork.getId());
                return addedArtwork;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ArtworkDto findArtworkById(@PathVariable Long id) {
        log.debug("Fetching artwork with ID: {}", id);
        return ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.findArtworkById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ARTIST')")
    public ArtworkDto updateArtwork(
            @PathVariable Long id,
            @RequestBody @Valid ArtworkUpdateDto artworkUpdateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ArtworkDto artworkN = ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.updateArtwork(id, ArtworkUpdateMapper.INSTANCE.artworkUpdateDtoToArtworkUpdate(artworkUpdateDto)));
        log.info("Artwork updated with ID: {}", artworkN.getId());
        return artworkN;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ARTIST')")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        try {
            log.info("Deleting artwork with ID: {}", id);
            artworkService.deleteArtwork(id);
            log.info("Artwork deleted with ID: {}", id);
            return ResponseEntity.ok("Artwork deleted successfully");
        } catch (NoSuchArtworkException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artwork not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete artwork");
        }
    }

    @PutMapping("/{id}/title")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateTitle(@PathVariable Long id, @RequestParam @Valid String title) {
        log.info("Updating title for artwork with ID: {}", id);
        artworkService.updateTitle(id, title);
        log.info("Title updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/description")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateDescription(@PathVariable Long id, @RequestParam @Valid String description) {
        log.info("Updating description for artwork with ID: {}", id);
        artworkService.updateDescription(id, description);
        log.info("Description updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/technique")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateTechnique(@PathVariable Long id, @RequestParam @Valid String technique) {
        log.info("Updating technique for artwork with ID: {}", id);
        artworkService.updateTechnique(id, technique);
        log.info("Technique updated for artwork with ID: {}", id);

    }

    @GetMapping("/byTitle")
    public List<ArtworkDto> getArtworksByTitle(@RequestParam @Valid String title) {
        log.debug("Fetching artworks with title: {}", title);
        List<Artwork> artworks = (artworkService.getArtworksByTitle(title));
        return artworks.stream().map(ArtworkMapper.INSTANCE::artworkToArtworkDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}/sold")
    public boolean isSold(@PathVariable Long id) {
        return artworkService.isSold(id);
    }

    @PostMapping("/{artworkId}/ratings")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void addRating(@PathVariable Long artworkId, @RequestBody @Valid RatingDto ratingDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        if (artworkService.existsRatingByCurator(ratingDto.getUser().getId(), artworkId))
            throw new TooManyRatingsException();
        log.info("Adding rating with ID {} to artwork with ID: {}", ratingDto.getId(), artworkId);
        artworkService.addRating(artworkId, RatingMapper.INSTANCE.ratingDtoToRating(ratingDto));
        log.info("Rating added to artwork with ID: {}", artworkId);
    }

    @DeleteMapping("/{artworkId}/ratings")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void deleteRating(@PathVariable Long artworkId, @RequestBody @Valid RatingDto ratingDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        log.info("Removing rating with ID {} from artwork with ID: {}", ratingDto.getId(), artworkId);
        artworkService.deleteRating(artworkId, RatingMapper.INSTANCE.ratingDtoToRating(ratingDto));
        log.info("Rating removed from artwork with ID: {}", artworkId);
    }

    @GetMapping("/{artworkId}/rating")
    public List<RatingDto> getAllRating(@PathVariable Long artworkId) {
        log.debug("Getting all rating for artwork with id " + artworkId);
        return artworkService.getAllRating(artworkId)
                .stream()
                .map(RatingMapper.INSTANCE::ratingToRatingDto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(NoSuchArtworkException.class)
    public ResponseEntity<String> handleNoSuchArtworkException(NoSuchArtworkException e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artwork not found: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        log.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
