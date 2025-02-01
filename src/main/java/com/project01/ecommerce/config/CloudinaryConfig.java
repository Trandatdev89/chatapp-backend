package com.project01.ecommerce.config;

import com.cloudinary.Cloudinary;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig{


    @Value("${LEGEND.CLOUD_NAME}")
    @NonFinal
    private String CLOUD_NAME;

    @Value("${LEGEND.API_KEY}")
    @NonFinal
    private String API_KEY;

    @Value("${LEGEND.API_SECRET}")
    @NonFinal
    private String API_SECRET;


    @Bean
    public Cloudinary cloudinary(){
        Map<String,Object> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }

}