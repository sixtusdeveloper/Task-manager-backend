package net.sixtusdev.taskmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allows CORS for all API endpoints starting with "/api/"
        registry.addMapping("/api/**")
                // Allows requests from this origin (e.g., Angular app running at
                // localhost:4200)
                .allowedOrigins("http://localhost:4200")
                // Permits these HTTP methods
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // Allows cookies and credentials to be sent with requests
                .allowCredentials(true);
    }
}
