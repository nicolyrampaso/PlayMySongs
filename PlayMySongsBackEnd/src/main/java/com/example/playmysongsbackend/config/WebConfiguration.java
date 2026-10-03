package com.example.playmysongsbackend.config;

import com.example.playmysongsbackend.services.MusicaService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    // serve /uploads/** direto da pasta fisica, assim a musica recem-enviada toca sem reiniciar
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String local = MusicaService.PASTA_UPLOADS.toUri().toString();
        if (!local.endsWith("/"))
            local += "/";
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(local);
    }
}
