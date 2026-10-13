package fr.cfa.hospital.core.tools;

import fr.cfa.hospital.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Utility class providing file handling operations on the server filesystem.
 * Handles secure file storage with generated unique identifiers and physical deletion.
 */
public class FileUtils {

    /**
     * Private constructor to prevent instantiation of static utility class.
     */
    private FileUtils() {
    }

    /**
     * Uploads and stores a multipart file onto the filesystem under a generated UUID name.
     * Creates destination directories if they do not exist.
     *
     * @param multipartFile the incoming uploaded file payload
     * @param uploadDir     the target directory path on the server
     * @return a {@link File} metadata object containing the original and generated file names
     * @throws IllegalArgumentException if the provided file is null or empty
     * @throws IOException              if an I/O error occurs during directory creation or file writing
     */
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

    /**
     * Deletes a stored file from the server filesystem if it exists.
     *
     * @param file      the file metadata entity containing the stored filename
     * @param uploadDir the directory path where the file is stored
     * @throws IOException if an I/O error occurs during deletion
     */
    public static void delete(File file, String uploadDir) throws IOException {
        if (file == null || file.getStoredFileName() == null) {
            return;
        }

        Path filePath = Paths.get(uploadDir).resolve(file.getStoredFileName());
        Files.deleteIfExists(filePath);
    }

    /**
     * Extracts the file extension including the leading dot from a given filename.
     *
     * @param filename the full filename
     * @return the file extension (e.g., ".png"), or an empty string if no extension is present
     */
    private static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            return filename.substring(dotIndex);
        }
        return "";
    }
}