package fr.cfa.hospital.core.tools;

import fr.cfa.hospital.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUtils {
    public static File upload(MultipartFile multipartFile, String uploadDir) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("The file can't be empty or null");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "unknown_file";
        }

        String extension = getExtension(originalFilename);
        String generatedFilename = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path destination = uploadPath.resolve(generatedFilename);
        Files.copy(multipartFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return new File(originalFilename, generatedFilename);
    }

    public static void delete(File file, String uploadDir) throws IOException {
        if (file == null || file.getStoredFileName() == null) {
            return;
        }

        Path filePath = Paths.get(uploadDir).resolve(file.getStoredFileName());
        Files.deleteIfExists(filePath);
    }

    private static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            return filename.substring(dotIndex);
        }
        return "";
    }
}
