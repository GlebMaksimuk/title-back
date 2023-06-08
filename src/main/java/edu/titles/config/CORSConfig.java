package edu.titles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
public class CORSConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new MyWebMvcConfigurer();
    }

    private static class MyWebMvcConfigurer implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry
                    .addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
    }
}
