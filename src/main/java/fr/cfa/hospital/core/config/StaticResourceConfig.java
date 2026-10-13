package fr.cfa.hospital.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Configuration for serving static resources uploaded to the server.
 * Maps incoming HTTP requests to physical directories on the local filesystem.
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    /**
     * Filesystem path where uploaded files are stored, injected from application properties.
     */
    @Value("${file.storage.path}")
    private String uploadDir;

    /**
     * Registers resource handlers to expose uploaded files through public URL paths.
     * Converts the configured relative or absolute directory path to a valid file URI scheme.
     *
     * @param registry the registry used to configure resource handlers and target physical locations
     */
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