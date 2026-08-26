package fr.cfa.hospital.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Value("${file.storage.path}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toUri().toString();

        if (!absolutePath.endsWith("/")) {
            absolutePath += "/";
        }

        registry.addResourceHandler("/files/**")
                .addResourceLocations(absolutePath);
    }
}
