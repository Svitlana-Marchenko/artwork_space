package com.system.artworkspace.utils;

import com.system.artworkspace.exhibition.Exhibition;
import com.system.artworkspace.exhibition.ExhibitionEntity;
import com.system.artworkspace.exhibition.exhibitionUpdate.ExhibitionUpdate;

import java.util.ArrayList;
import java.util.Date;

import static com.system.artworkspace.utils.Utils.getRandomLong;
import static com.system.artworkspace.utils.Utils.getRandomString;

public class ExhibitionUtils {

    public static Exhibition getRandomExhibition() {
        return new Exhibition(getRandomLong(), getRandomString(), getRandomString(), UserUtils.getRandomUser(), new ArrayList<>(), new Date(), new Date());
    }

    public static ExhibitionEntity getRandomExhibitionEntity() {
        return new ExhibitionEntity(getRandomLong(), UserUtils.getRandomUserEntity(), getRandomString(), getRandomString(),  new ArrayList<>(), new Date(), new Date());
    }

    public static ExhibitionUpdate getRandomExhibitionUpdate() {
        return new ExhibitionUpdate(getRandomLong(), getRandomString(), getRandomString());
    }
}
