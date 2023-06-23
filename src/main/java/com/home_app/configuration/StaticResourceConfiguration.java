package com.home_app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/planespotting/images/**")
                .addResourceLocations("classpath:/static/planespotting/images/")
                .setCachePeriod(0); // Set the cache period to 0 for immediate revalidation
    }

    @Bean
    public CacheUtils cacheUtils() {
        return new CacheUtils();
    }
}