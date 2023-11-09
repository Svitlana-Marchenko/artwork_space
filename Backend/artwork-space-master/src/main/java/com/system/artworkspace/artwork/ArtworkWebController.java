package com.system.artworkspace.artwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
