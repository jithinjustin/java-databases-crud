package com.project.code.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all endpoints
        registry.addMapping("/**")
                .allowedOrigins("*")  // Add your frontend URL here
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specify allowed methods
                .allowedHeaders("*");  // You can restrict headers if needed
    }
}
