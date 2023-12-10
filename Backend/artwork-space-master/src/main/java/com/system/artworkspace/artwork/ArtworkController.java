package com.system.artworkspace.artwork;

import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdate;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateDto;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdateMapper;
import com.system.artworkspace.exceptions.ExceptionHelper;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import com.system.artworkspace.exceptions.ValidationException;
import com.system.artworkspace.exhibition.ExhibitionDto;
import com.system.artworkspace.exhibition.ExhibitionMapper;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateDto;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdateMapper;
import com.system.artworkspace.helpers.RateLimit;
import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingMapper;
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

import java.util.List;
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
    public ArtworkDto addArtwork(@RequestBody @Valid ArtworkDto artwork, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.info("Adding artwork with ID: {}", artwork.getId());
        ArtworkDto addedArtwork = ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.addArtwork(ArtworkMapper.INSTANCE.artworkDtoToArtwork(artwork)));
        logger.info("Artwork added successfully.");
        return addedArtwork;
    }

    @GetMapping("/{id}")
    public ArtworkDto findArtworkById(@PathVariable Long id) {
        logger.info("Fetching artwork with ID: {}", id);
        return ArtworkMapper.INSTANCE.artworkToArtworkDto(artworkService.findArtworkById(id));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ARTIST')")
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
        logger.info("Artwork updated with ID: {}", artworkN.getId());
        return artworkN;
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ARTIST')")
    public void deleteArtwork(@PathVariable Long id) {
        logger.info("Deleting artwork with ID: {}", id);
        artworkService.deleteArtwork(id);
        logger.info("Artwork deleted with ID: {}", id);
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
    @PostMapping("/{artworkId}/addRating")
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

    @DeleteMapping("/{artworkId}/deleteRating")
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
