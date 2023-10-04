package com.system.artworkspace.collection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "collection")
public class CollectionConfiguration {
    private int maxSize;
    private boolean enabledToCreate;

    public int getMaxSize() {
        return maxSize;
    }

    public boolean isEnabled() {
        return enabledToCreate;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setEnabledToCreate(boolean enabledToCreate) {
        this.enabledToCreate = enabledToCreate;
    }
}
