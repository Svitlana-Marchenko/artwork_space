package com.system.artworkspace.validation;

import com.system.artworkspace.artwork.Artwork;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@ConditionalOnClass(Artwork.class)
public class ArtworkValidator {
    private long fileSizeLimitInMB;

    public ArtworkValidator() {
        this.fileSizeLimitInMB = 1000;
        //this.fileSizeLimitInMB = Long.parseLong(System.getProperty("custom.file-size-limit"));
    }

    public boolean isFileSizeValid(long fileSizeInBytes) {
        return fileSizeInBytes <= (fileSizeLimitInMB * 1024 * 1024);
    }
}
