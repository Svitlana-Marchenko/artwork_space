package com.system.artworkspace.utils;

import com.system.artworkspace.collection.Collection;
import com.system.artworkspace.collection.CollectionEntity;

import java.util.ArrayList;

import static com.system.artworkspace.utils.Utils.getRandomLong;
import static com.system.artworkspace.utils.Utils.getRandomString;

public class CollectionUtils {

    public static Collection getRandomCollection() {
        return new Collection(getRandomLong(), UserUtils.getRandomUser(), getRandomString(), new ArrayList<>());
    }

    public static CollectionEntity getRandomCollectionEntity() {
        return new CollectionEntity(getRandomLong(), UserUtils.getRandomUserEntity(), getRandomString(), new ArrayList<>());
    }
}
