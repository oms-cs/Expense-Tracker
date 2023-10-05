package com.springbooot.tutorials.springmongodbdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.cors-config}")
    private String corsConfiguration;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(corsConfiguration).allowedMethods(allowedMethods).allowedHeaders(allowedHeaders);
    }
}
