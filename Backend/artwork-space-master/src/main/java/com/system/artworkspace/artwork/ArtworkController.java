package com.system.artworkspace.artwork;

import com.system.artworkspace.exceptions.NoSuchArtworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/artworks")
public class ArtworkController {
    private static final Logger logger = LoggerFactory.getLogger(ArtworkController.class);

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @PostMapping
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
    public void deleteArtwork(@PathVariable Long id) {
        logger.info("Deleting artwork with ID: {}", id);
        artworkService.deleteArtwork(id);
        logger.info("Artwork deleted with ID: {}", id);
    }

    @PutMapping("/{id}/title")
    public void updateTitle(@PathVariable Long id, @RequestParam @Valid String title, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Updating title for artwork with ID: {}", id);
        artworkService.updateTitle(id, title);
        logger.info("Title updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/description")
    public void updateDescription(@PathVariable Long id, @RequestParam @Valid String description, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Updating description for artwork with ID: {}", id);
        artworkService.updateDescription(id, description);
        logger.info("Description updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/technique")
    public void updateTechnique(@PathVariable Long id, @RequestParam @Valid String technique, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Updating technique for artwork with ID: {}", id);
        artworkService.updateTechnique(id, technique);
        logger.info("Technique updated for artwork with ID: {}", id);
    }

    @GetMapping("/byTitle")
    public List<ArtworkDto> getArtworksByTitle(@RequestParam @Valid String title, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logErrors(bindingResult);
        }
        logger.info("Fetching artworks with title: {}", title);
        List<Artwork> artworks = (artworkService.getArtworksByTitle(title));

        return (List<ArtworkDto>) artworks.stream().map(x -> ArtworkMapper.INSTANCE.artworkToArtworkDto(x));

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
}
