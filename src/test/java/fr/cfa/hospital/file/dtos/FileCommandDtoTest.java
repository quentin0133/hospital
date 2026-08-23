package fr.cfa.hospital.file.dtos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FileCommandDtoTest {
    @Test
    void testConstructorEmpty_validInput() {
        FileCommandDto dto = new FileCommandDto();

        assertNotNull(dto);
        assertEquals(0, dto.getId());
        assertNull(dto.getFile());
    }

    @Test
    void testConstructorFull_validInput() {
        FileCommandDto dto = new FileCommandDto(1, null);

        assertEquals(1, dto.getId());
        assertNull(dto.getFile());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new FileCommandDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new FileCommandDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        FileCommandDto f1 = new FileCommandDto();
        f1.setId(1);

        FileCommandDto f2 = new FileCommandDto();
        f2.setId(2);

        assertNotEquals(f1, f2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        FileCommandDto f1 = new FileCommandDto();
        f1.setId(1);
        f1.setFile(null);

        FileCommandDto f2 = new FileCommandDto();
        f2.setId(1);
        f2.setFile(null);

        assertEquals(f1, f2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        FileCommandDto f1 = new FileCommandDto();
        f1.setId(1);
        f1.setFile(null);

        FileCommandDto f2 = new FileCommandDto();
        f2.setId(1);
        f2.setFile(null);

        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void testToString() {
        FileCommandDto file = new FileCommandDto();
        file.setId(1);
        file.setFile(null);

        String expected = "FileUploadDto{" +
            "id=" + file.getId() +
            '}';
        assertEquals(expected, file.toString());
    }
}