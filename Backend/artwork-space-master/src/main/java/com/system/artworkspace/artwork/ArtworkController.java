package com.system.artworkspace.artwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ArtworkDto addArtwork(@RequestBody ArtworkDto artwork) {
        logger.info("Adding artwork with ID: {}", artwork.getId());
        ArtworkDto addedArtwork = artworkService.addArtwork(artwork);
        logger.info("Artwork added successfully.");
        return addedArtwork;
    }

    @GetMapping("/{id}")
    public ArtworkDto findArtworkById(@PathVariable Long id) {
        logger.info("Fetching artwork with ID: {}", id);
        return artworkService.findArtworkById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteArtwork(@PathVariable Long id) {
        logger.info("Deleting artwork with ID: {}", id);
        artworkService.deleteArtwork(id);
        logger.info("Artwork deleted with ID: {}", id);
    }

    @PutMapping("/{id}/title")
    public void updateTitle(@PathVariable Long id, @RequestParam String title) {
        logger.info("Updating title for artwork with ID: {}", id);
        //artworkService.updateTitle(id, title);
        logger.info("Title updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/description")
    public void updateDescription(@PathVariable Long id, @RequestParam String description) {
        logger.info("Updating description for artwork with ID: {}", id);
        //artworkService.updateDescription(id, description);
        logger.info("Description updated for artwork with ID: {}", id);
    }

    @PutMapping("/{id}/technique")
    public void updateTechnique(@PathVariable Long id, @RequestParam String technique) {
        logger.info("Updating technique for artwork with ID: {}", id);
        //artworkService.updateTechnique(id, technique);
        logger.info("Technique updated for artwork with ID: {}", id);
    }

    @GetMapping("/byTitle")
    public List<ArtworkDto> getArtworksByTitle(@RequestParam String title) {
        logger.info("Fetching artworks with title: {}", title);
        List<ArtworkDto> artworks = artworkService.getArtworksByTitle(title);
        return artworks;
    }
}
