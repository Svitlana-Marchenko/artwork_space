package org.rating;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StarterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CountRatingService countRatingService(){
        return new CountRating();
    }

    @Bean
    @ConditionalOnMissingBean
    public CountPriceService countPriceService(){
        return new CountPrice();
    }
}
