package com.system.artworkspace.artwork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateDto;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import com.system.artworkspace.exceptions.NoSuchUserException;
import com.system.artworkspace.exceptions.ValidationException;
import com.system.artworkspace.helpers.RateLimit;
import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingMapper;
import javax.validation.Valid;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/artworks")
public class ArtworkController {
    private static final Logger logger = LoggerFactory.getLogger(ArtworkController.class);

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public List<ArtworkDto> getAll(){
        logger.info("Getting all artworks");
        return artworkService.getAllArtwork().stream().map(x-> ArtworkMapper.INSTANCE.artworkToArtworkDto(x)).collect(Collectors.toList());
    }
    @GetMapping("/artist/{id}")
    public List<ArtworkDto> getAllByUserId(@PathVariable Long id){
        logger.info("Getting all artworks");
        return artworkService.getAllArtworkByArtistId(id).stream().map(x-> ArtworkMapper.INSTANCE.artworkToArtworkDto(x)).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ARTIST')")
    @RateLimit(maxCalls = 2)
    public ArtworkDto addArtwork( @RequestPart("file") MultipartFile file,
                                  @RequestPart("artwork") @Valid String artworkJson,
                                  BindingResult bindingResult){
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
            }else{
                ArtworkDto addedArtwork = ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork),file));
                logger.info("Artwork added successfully with ID: {}", addedArtwork.getId());
                return addedArtwork;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ArtworkDto findArtworkById(@PathVariable Long id) {
        logger.info("Fetching artwork with ID: {}", id);
        return ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.findArtworkById(id));
    }

    @PutMapping
    //@PreAuthorize("hasAuthority('ARTIST')")
    //@PreAuthorize("#user.id == authentication.id")
    public ArtworkDto updateArtwork(
            @RequestBody @Valid ArtworkUpdateDto artworkUpdateDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        ArtworkDto artworkN = ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.updateArtwork(ArtworkUpdateMapper.INSTANCE.artworkUpdateDtoToArtworkUpdate(artworkUpdateDto)));
        logger.info("Artwork updated with ID: {}", artworkN.getId());
        return artworkN;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        try {
            logger.info("Deleting artwork with ID: {}", id);
            artworkService.deleteArtwork(id);
            logger.info("Artwork deleted with ID: {}", id);
            return ResponseEntity.ok("Artwork deleted successfully");
        }catch (NoSuchArtworkException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artwork not found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete artwork");
        }
    }

    @PutMapping("/{id}/title")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateTitle(@PathVariable Long id, @RequestParam @Valid String title) {
        logger.info("Updating title for artwork with ID: {}", id);
        artworkService.updateTitle(id, title);
        logger.info("Title updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/description")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateDescription(@PathVariable Long id, @RequestParam @Valid String description) {

        logger.info("Updating description for artwork with ID: {}", id);
        artworkService.updateDescription(id, description);
        logger.info("Description updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/technique")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateTechnique(@PathVariable Long id, @RequestParam @Valid String technique) {
        logger.info("Updating technique for artwork with ID: {}", id);
        artworkService.updateTechnique(id, technique);
        logger.info("Technique updated for artwork with ID: {}", id);

    }

    @GetMapping("/byTitle")
    public List<ArtworkDto> getArtworksByTitle(@RequestParam @Valid String title) {

        logger.info("Fetching artworks with title: {}", title);
        List<Artwork> artworks = (artworkService.getArtworksByTitle(title));

        return artworks.stream().map(x -> ArtworkMapper.INSTANCE.artworkToArtworkDto(x)).collect(Collectors.toList());

    }

    @ExceptionHandler(NoSuchArtworkException.class)
    public ResponseEntity<String> handleNoSuchArtworkException(NoSuchArtworkException e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artwork not found: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/{artworkId}/ratings")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void addRating(@PathVariable Long artworkId, @RequestBody @Valid RatingDto ratingDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Adding rating with ID {} to artwork with ID: {}", ratingDto.getId(), artworkId);
        artworkService.addRating(artworkId, RatingMapper.INSTANCE.ratingDtoToRating(ratingDto));
        logger.info("Rating added to artwork with ID: {}", artworkId);
    }

    @DeleteMapping("/{artworkId}/ratings")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void deleteRating(@PathVariable Long artworkId, @RequestBody @Valid RatingDto ratingDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Removing rating with ID {} from artwork with ID: {}", ratingDto.getId(), artworkId);
        artworkService.deleteRating(artworkId, RatingMapper.INSTANCE.ratingDtoToRating(ratingDto));
        logger.info("Rating removed from artwork with ID: {}", artworkId);
    }

    @GetMapping("/{artworkId}/rating")
    public List<RatingDto> getAllRating(@PathVariable Long artworkId) {
        logger.info("Getting all rating for artwork with id " + artworkId);
        return artworkService.getAllRating(artworkId)
                .stream()
                .map(x -> RatingMapper.INSTANCE.ratingToRatingDto(x))
                .collect(Collectors.toList());
    }

}
