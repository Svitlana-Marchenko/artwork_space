package com.system.artworkspace.artwork;

import com.system.artworkspace.exceptions.NoSuchArtworkException;
import com.system.artworkspace.rating.Rating;
import com.system.artworkspace.rating.RatingDto;
import com.system.artworkspace.rating.RatingMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    @PostMapping
    @PreAuthorize("hasAuthority('ARTIST')")
    public ArtworkDto addArtwork(@RequestBody @Valid ArtworkDto artwork, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void deleteArtwork(@PathVariable Long id) {
        logger.info("Deleting artwork with ID: {}", id);
        try {
            artworkService.deleteArtwork(id);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found", e);

        }
        logger.info("Artwork deleted with ID: {}", id);
    }

    @PutMapping("/{id}/title")
    @PreAuthorize("hasAuthority('ARTIST')")
    public void updateTitle(@PathVariable Long id, @RequestParam @Valid String title) {
      //  if (bindingResult.hasErrors()) {
        //    logErrors(bindingResult);
       // }
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artwork not found: " + e.getMessage());
    }

    private void logErrors (BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError o : allErrors){
            logger.info("error -->  " + o.getDefaultMessage());
        }
    }
    @PostMapping("/{artworkId}/addRating")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void addRating(@PathVariable Long artworkId, @RequestBody RatingDto ratingDto) {
        logger.info("Adding rating with ID {} to artwork with ID: {}", ratingDto.getId(), artworkId);
        artworkService.addRating(artworkId, RatingMapper.INSTANCE.ratingDtoToRating(ratingDto));
        logger.info("Rating added to artwork with ID: {}", artworkId);
    }

    //todo debug
    @DeleteMapping("/{artworkId}/deleteRating")
    @PreAuthorize("hasAuthority('CURATOR')")
    public void deleteRating(@PathVariable Long artworkId, @RequestBody RatingDto ratingDto) {
        logger.info("Removing rating with ID {} from artwork with ID: {}", ratingDto.getId(), artworkId);
        artworkService.deleteRating(artworkId, RatingMapper.INSTANCE.ratingDtoToRating(ratingDto));
        logger.info("Rating removed from artwork with ID: {}", artworkId);
    }

    @GetMapping("/{artworkId}/rating")
    public List <RatingDto> getAllRating (@PathVariable Long artworkId){
        logger.info("Getting all rating for artwork with id "+artworkId);
        return artworkService.getAllRating(artworkId).stream().map(x -> RatingMapper.INSTANCE.ratingToRatingDto(x)).collect(Collectors.toList());

    }
}
