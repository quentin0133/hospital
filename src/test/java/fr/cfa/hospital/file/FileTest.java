package fr.cfa.hospital.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FileTest {

    @Test
    void testConstructorEmpty_validInput() {
        File dto = new File();

        assertNotNull(dto);
        assertNull(dto.getFileName());
        assertNull(dto.getStoredFileName());
    }

    @Test
    void testConstructorFull_validInput() {
        File dto = new File("file.pdf", "file-215.pdf");

        assertEquals("file.pdf", dto.getFileName());
        assertEquals("file-215.pdf", dto.getStoredFileName());
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        File original = new File();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new File());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new File());
    }

    @Test
    void testEquals_differentFileName() {
        File f1 = new File();
        f1.setFileName("1");
        File f2 = new File();
        f1.setFileName("2");

        assertNotEquals(f1, f2);
    }

    @Test
    void testEquals_differentStoredFileName() {
        File f1 = new File();
        f1.setStoredFileName("1");
        File f2 = new File();
        f1.setStoredFileName("2");

        assertNotEquals(f1, f2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        File c1 = new File();
        c1.setFileName("file.pdf");
        c1.setStoredFileName("file-215.pdf");

        File c2 = new File();
        c2.setFileName("file.pdf");
        c2.setStoredFileName("file-215.pdf");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        File c1 = new File();
        c1.setFileName("file.pdf");
        c1.setStoredFileName("file-215.pdf");

        File c2 = new File();
        c2.setFileName("file.pdf");
        c2.setStoredFileName("file-215.pdf");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        File file = new File();
        file.setFileName("file.pdf");
        file.setStoredFileName("file-215.pdf");

        String expected = "File{" +
            "fileName='" + file.getFileName() + '\'' +
            ", storedFileName='" + file.getStoredFileName() + '\'' +
            '}';
        assertEquals(expected, file.toString());
    }
}