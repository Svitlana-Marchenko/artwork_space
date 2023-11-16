package com.system.artworkspace.artwork;

import com.system.artworkspace.security.UserInfoUserDetails;
import com.system.artworkspace.user.UserEntity;
import com.system.artworkspace.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @GetMapping("/image/new")
    public String showAddPictureForm(Model model) {
        model.addAttribute("artworkForm", new ArtworkForm());
        return "artwork/addArtwork";
    }

    @PostMapping("/image/new")
    public String addArtwork(@ModelAttribute ArtworkForm pictureForm, Model model) {

        UserInfoUserDetails userDetails = (UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user = userDetails.getUser();

        MultipartFile imageFile = pictureForm.getImageFile();

        try {
            String imagePath = "src/main/resources/static/images/" + imageFile.getOriginalFilename();
            String projectPath = new File("").getAbsolutePath();
            String fullPath = projectPath + "/" + imagePath;
            imageFile.transferTo(new File(fullPath));



            Artwork artwork = new Artwork();
            artwork.setTitle(pictureForm.getTitle());
            artwork.setDescription(pictureForm.getDescription());
            artwork.setTechnique(pictureForm.getTechnique());
            artwork.setWidth(Integer.parseInt(pictureForm.getWidth()));
            artwork.setHeight(Integer.parseInt(pictureForm.getHeight()));
            artwork.setUser(UserMapper.INSTANCE.userEntityToUser(user));
            artwork.setImageURL("images/" + imageFile.getOriginalFilename());

            artworkService.addArtwork(artwork);

            return "redirect:/all-images";
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading image.");
            logger.error(e.getMessage());
            return "artwork/addArtwork";
        }
    }
}
