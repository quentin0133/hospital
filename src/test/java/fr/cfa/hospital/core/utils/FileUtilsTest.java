package fr.cfa.hospital.core.utils;

import fr.cfa.hospital.core.tools.FileUtils;
import fr.cfa.hospital.file.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FileUtilsTest {
    @TempDir
    Path tempDir;

    @Test
    void upload_directoryNotExists() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
            "file",
            "test-document.pdf",
            "application/pdf",
            "some data".getBytes()
        );

        Path nonExistentDir = tempDir.resolve("dossier_fantome");
        String uploadDirPath = nonExistentDir.toString();

        assertFalse(Files.exists(nonExistentDir));

        File result = FileUtils.upload(mockFile, uploadDirPath);

        assertNotNull(result);
        assertEquals("test-document.pdf", result.getFileName());
        assertNotNull(result.getStoredFileName());

        Path expectedFilePath = nonExistentDir.resolve(result.getStoredFileName());
        assertTrue(Files.exists(expectedFilePath));
    }

    @Test
    void upload_directoryExists() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-document.pdf",
                "application/pdf",
                "some data".getBytes()
        );

        String uploadDirPath = tempDir.toString();

        assertTrue(Files.exists(tempDir));

        File result = FileUtils.upload(mockFile, uploadDirPath);

        assertNotNull(result);
        assertEquals("test-document.pdf", result.getFileName());
        assertNotNull(result.getStoredFileName());

        Path expectedFilePath = tempDir.resolve(result.getStoredFileName());
        assertTrue(Files.exists(expectedFilePath));
    }

    @Test
    void upload_fileEmpty() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-document.pdf",
                "application/pdf",
                new byte[0]
        );

        assertThrows(IllegalArgumentException.class, () -> FileUtils.upload(mockFile, "files-test"), "The file can't be empty or null");
    }

    @Test
    void upload_fileNull() throws IOException {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS)) {
            assertThrows(IllegalArgumentException.class, () -> FileUtils.upload(null, "files-test"), "The file can't be empty or null");

            fileMocked.verify(() -> Files.createDirectories(any(Path.class)), never());
            fileMocked.verify(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)), never());
        }
    }

    @Test
    void delete() throws IOException {
        String storedName = "test.txt";
        Path fakeFile = tempDir.resolve(storedName);
        Files.writeString(fakeFile, "Lorem ipsum dolor sit amet");

        assertTrue(Files.exists(fakeFile));

        File file = new File("original.txt", storedName);
        String uploadDirPath = tempDir.toString();

        FileUtils.delete(file, uploadDirPath);
        assertFalse(Files.exists(fakeFile));
    }

    @Test
    void delete_directoryNotExists() throws IOException {
        String storedName = "does_not_exist.txt";
        File file = new File("", "");

        assertDoesNotThrow(() -> FileUtils.delete(file, storedName));
    }

    @Test
    void delete_fileNull() {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS);
             MockedStatic<Paths> pathsMocked = mockStatic(Paths.class, CALLS_REAL_METHODS)) {
            assertDoesNotThrow(() -> FileUtils.delete(null, "files-test"));

            fileMocked.verifyNoInteractions();
            pathsMocked.verifyNoInteractions();
        }
    }
}