package com.system.artworkspace.artwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArtworkWebController {

    private static final Logger logger = LoggerFactory.getLogger(ArtworkController.class);

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkWebController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping("/all-images")
    public String getAll(Model model){
        logger.info("Getting all artworks");
        List <ArtworkDto> list = artworkService.getAllArtwork().stream().map(x-> ArtworkMapper.INSTANCE.artworkToArtworkDto(x)).collect(Collectors.toList());
        model.addAttribute("images", list);
        return "artwork/index";
    }

    @GetMapping("/image/{id}")
    public String findArtworkById(@PathVariable("id") long id, Model model){
        logger.info("Getting one artwork");
        Artwork artwork = artworkService.findArtworkById(id);
        ArtworkDto picture = ArtworkMapper.INSTANCE.artworkToArtworkDto(artwork);
        model.addAttribute("picture", picture);
        return "artwork/artwork";
    }
}
