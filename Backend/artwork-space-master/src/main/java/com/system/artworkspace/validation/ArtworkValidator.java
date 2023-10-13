package com.system.artworkspace.validation;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.system.artworkspace.logger.LoggingMarkers.ARTWORK_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_USER_EVENTS;

@ConditionalOnClass(Artwork.class)
public class ArtworkValidator {
    private long fileSizeLimitInMB;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    public ArtworkValidator() {
        this.fileSizeLimitInMB = 1000;
        //this.fileSizeLimitInMB = Long.parseLong(System.getProperty("custom.file-size-limit"));
    }
    public static boolean validateTitle(String title){
        logger.info(ARTWORK_EVENTS,"Validation of title of artwork ID: {}", ThreadContext.get("artwork_id"));

        String regex = "^[A-Za-z0-9!?(),._\\s]{1,50}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(title);

        return matcher.matches();
    }

    public static boolean validateImageURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            logger.info(ARTWORK_EVENTS,"Validation of imageUrl of artwork ID: {}", ThreadContext.get("artwork_id"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                if (contentType != null && contentType.startsWith("image")) {
                    return true;
                }
            }
        } catch (IOException e) {
            logger.warn(ARTWORK_EVENTS,"Validation of imageURL failed for artwork ID: {}", ThreadContext.get("artwork_id"));
        }

        return false;
    }

    public static boolean validateTechnique(String technique){
        logger.info(ARTWORK_EVENTS,"Validation of technique of artwork ID: {}", ThreadContext.get("artwork_id"));

        String regex = "^[A-Za-z0-9(),\\s]{1,100}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(technique);

        return matcher.matches();
    }

    public boolean isFileSizeValid(long fileSizeInBytes) {
        return fileSizeInBytes <= (fileSizeLimitInMB * 1024 * 1024);
    }
}
