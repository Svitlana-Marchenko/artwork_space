package com.system.artworkspace.utils;

import com.system.artworkspace.artwork.Artwork;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.artworkUpdate.ArtworkUpdate;
import com.system.artworkspace.rating.Rating;

import java.util.ArrayList;

import static com.system.artworkspace.utils.Utils.getRandomLong;
import static com.system.artworkspace.utils.Utils.getRandomString;

public class ArtworkUtils {

    public static ArtworkEntity getRandomArtworkEntity() {
        return new ArtworkEntity(getRandomLong(), getRandomString(), getRandomString(), getRandomString(), 100.0, 150.0, UserUtils.getRandomUserEntity(), getRandomString(), new ArrayList<>());
    }

    public static Artwork getRandomArtwork() {
        return new Artwork(getRandomLong(), getRandomString(), getRandomString(), getRandomString(), 100.0, 150.0, UserUtils.getRandomUser(), getRandomString(), new ArrayList<>());
    }

    public static ArtworkUpdate getRandomArtworkUpdate() {
        return new ArtworkUpdate(getRandomString(), getRandomString(), getRandomString(), 100.0, 150.0, UserUtils.getRandomUser());
    }

    public static Rating getRandomRating() {
        return new Rating(getRandomLong(), 100.0, UserUtils.getRandomUser(), getRandomString());
    }

}
