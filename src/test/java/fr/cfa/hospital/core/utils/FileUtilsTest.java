package fr.cfa.hospital.core.utils;

import fr.cfa.hospital.file.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FileUtilsTest {

    @Test
    void upload_directoryNotExists() throws IOException {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS);
             MockedStatic<UUID> uuidMocked = mockStatic(UUID.class, CALLS_REAL_METHODS)) {
            Path mockPath = mock(Path.class);
            UUID uuid = UUID.randomUUID();
            File expected = new File("test.pdf", uuid + "-test.pdf");
            MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "Hello World".getBytes()
            );

            uuidMocked.when(UUID::randomUUID).thenReturn(uuid);
            fileMocked.when(() -> Files.exists(any(Path.class))).thenReturn(false);
            fileMocked.when(() -> Files.createDirectories(any(Path.class))).thenReturn(mockPath);
            fileMocked.when(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)))
                .thenReturn(1L);

            assertEquals(expected, FileUtils.upload(multipartFile, "files-test"));

            fileMocked.verify(() -> Files.createDirectories(any(Path.class)));
            fileMocked.verify(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)));
        }
    }

    @Test
    void upload_directoryExists() throws IOException {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS);
             MockedStatic<UUID> uuidMocked = mockStatic(UUID.class, CALLS_REAL_METHODS)) {
            UUID uuid = UUID.randomUUID();
            File expected = new File("test.pdf", uuid + "-test.pdf");
            MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "Hello World".getBytes()
            );

            uuidMocked.when(UUID::randomUUID).thenReturn(uuid);
            fileMocked.when(() -> Files.exists(any(Path.class))).thenReturn(true);
            fileMocked.when(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)))
                .thenReturn(1L);

            assertEquals(expected, FileUtils.upload(multipartFile, "files-test"));

            fileMocked.verify(() -> Files.createDirectories(any(Path.class)), never());
            fileMocked.verify(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)));
        }
    }

    @Test
    void upload_fileEmpty() throws IOException {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS);
             MockedStatic<UUID> uuidMocked = mockStatic(UUID.class, CALLS_REAL_METHODS)) {
            MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", new byte[0]
            );

            assertNull(FileUtils.upload(multipartFile, "files-test"));

            fileMocked.verify(() -> Files.createDirectories(any(Path.class)), never());
            fileMocked.verify(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)), never());
            uuidMocked.verifyNoInteractions();
        }
    }

    @Test
    void upload_fileNull() throws IOException {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS)) {
            assertNull(FileUtils.upload(null, "files-test"));

            fileMocked.verify(() -> Files.createDirectories(any(Path.class)), never());
            fileMocked.verify(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class)), never());
        }
    }

    @Test
    void delete() {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS);
             MockedStatic<Paths> pathsMocked = mockStatic(Paths.class, CALLS_REAL_METHODS)) {
            Path basePath = mock(Path.class);
            Path resolvedPath = mock(Path.class);
            Path normalizedPath = mock(Path.class);

            fileMocked.when(() -> Files.exists(any(Path.class))).thenReturn(true);
            fileMocked.when(() -> Files.delete(any(Path.class))).thenAnswer(invocation -> null);
            pathsMocked.when(() -> Paths.get(anyString())).thenReturn(basePath);
            when(basePath.resolve(anyString())).thenReturn(resolvedPath);
            when(resolvedPath.normalize()).thenReturn(normalizedPath);

            assertDoesNotThrow(() -> FileUtils.delete(new File("", ""), "files-test"));
        }
    }

    @Test
    void delete_directoryNotExists() {
        try (MockedStatic<Files> fileMocked = mockStatic(Files.class, CALLS_REAL_METHODS);
             MockedStatic<Paths> pathsMocked = mockStatic(Paths.class, CALLS_REAL_METHODS)) {
            Path basePath = mock(Path.class);
            Path resolvedPath = mock(Path.class);
            Path normalizedPath = mock(Path.class);

            fileMocked.when(() -> Files.exists(any(Path.class))).thenReturn(false);
            pathsMocked.when(() -> Paths.get(anyString())).thenReturn(basePath);
            when(basePath.resolve(anyString())).thenReturn(resolvedPath);
            when(resolvedPath.normalize()).thenReturn(normalizedPath);

            assertThrows(FileNotFoundException.class, () -> FileUtils.delete(new File("", ""), "files-test"));
        }
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