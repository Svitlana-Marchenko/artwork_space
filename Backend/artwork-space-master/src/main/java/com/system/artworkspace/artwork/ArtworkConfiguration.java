package com.system.artworkspace.artwork;

import com.system.artworkspace.validation.ArtworkValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtworkConfiguration {
    @Bean
    @ConditionalOnExpression("${custom.file-size-limit} >= 0")
    public ArtworkValidator fileSizeChecker() {
        return new ArtworkValidator();
    }
}
