package com.system.artworkspace.artwork;

import com.system.artworkspace.helpers.CustomCacheManager;
import com.system.artworkspace.validation.ArtworkValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ArtworkConfiguration {
    @Bean
    @ConditionalOnExpression("${custom.file-size-limit} >= 0")
    public ArtworkValidator fileSizeChecker() {
        return new ArtworkValidator();
    }

    @Bean
    public CacheManager cacheManager() {
        return new CustomCacheManager();
    }
}
