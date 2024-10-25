package com.system.artworkspace.artwork;

import com.system.artworkspace.helpers.CustomCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ArtworkConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new CustomCacheManager();
    }
}
