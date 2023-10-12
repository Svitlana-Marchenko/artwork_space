package com.system.artworkspace.logger;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LoggingMarkers {
    public static final Marker USER_ACTIONS = MarkerFactory.getMarker("USER_ACTIONS");
    public static final Marker ARTWORK_EVENTS = MarkerFactory.getMarker("ARTWORK_EVENTS");
    public static final Marker AUCTIONS_EVENTS = MarkerFactory.getMarker("AUCTIONS_EVENTS");
    public static final Marker EXHIBITION_EVENTS = MarkerFactory.getMarker("EXHIBITION_EVENTS");
    public static final Marker COLLECTION_EVENTS = MarkerFactory.getMarker("COLLECTION_EVENTS");
    public static final Marker CONFIDENTIAL_EVENTS = MarkerFactory.getMarker("CONFIDENTIAL_EVENTS");
    public static final Marker CONFIDENTIAL_USER_EVENTS = MarkerFactory.getMarker("CONFIDENTIAL_USER_EVENTS");
    static {
        CONFIDENTIAL_USER_EVENTS.add(CONFIDENTIAL_EVENTS);
        CONFIDENTIAL_USER_EVENTS.add(USER_ACTIONS);
    }
}
