package com.system.artworkspace.validation;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.Artwork;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

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
        return true;
    }

    public boolean isFileSizeValid(long fileSizeInBytes) {
        return fileSizeInBytes <= (fileSizeLimitInMB * 1024 * 1024);
    }
}
