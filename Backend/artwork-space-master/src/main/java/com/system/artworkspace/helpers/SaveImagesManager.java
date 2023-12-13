package com.system.artworkspace.helpers;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveImagesManager {
    private final static String PREFIX_FOR_ARTIST_FOLDER="artist_";
    private final static String PATH_TO_IMAGE="../data/"+PREFIX_FOR_ARTIST_FOLDER;
    private final static String ROOT_PATH=new File("").getAbsolutePath() + "\\Frontend\\artwork-space\\public\\data";
    public static void createFolderForArtist(Long id) {
        // Create the folder name using the artist's ID
        String folderName = PREFIX_FOR_ARTIST_FOLDER + id;
        Path artistFolderPath = Paths.get(ROOT_PATH, folderName);

        try {
            // Check if the folder doesn't exist, then create it
            if (Files.notExists(artistFolderPath)) {
                Files.createDirectory(artistFolderPath);
                System.out.println("Folder created successfully for artist with ID " + id);
            } else {
                System.out.println("Folder already exists for artist with ID " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating folder for artist with ID " + id);
        }
    }
    public static String saveImage(MultipartFile file, Long userId, Long artworkId) throws IOException {
        String imageName=artworkId+"."+getFileExtension(file.getOriginalFilename());
        String pathInProject = "\\"+PREFIX_FOR_ARTIST_FOLDER+userId +"\\"+ imageName;
        String imagePath = ROOT_PATH+pathInProject;
        file.transferTo(new File(imagePath));
        return PATH_TO_IMAGE+userId+"/"+imageName;
    }
    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return ""; // No file extension found
    }
}
