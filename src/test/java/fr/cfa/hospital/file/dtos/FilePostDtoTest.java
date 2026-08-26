package fr.cfa.hospital.file.dtos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FilePostDtoTest {
    @Test
    void testConstructorEmpty_validInput() {
        FilePostDto dto = new FilePostDto();

        assertNotNull(dto);
        assertEquals(0, dto.getId());
        assertNull(dto.getFile());
    }

    @Test
    void testConstructorFull_validInput() {
        FilePostDto dto = new FilePostDto(1, null);

        assertEquals(1, dto.getId());
        assertNull(dto.getFile());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new FilePostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new FilePostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        FilePostDto f1 = new FilePostDto();
        f1.setId(1);

        FilePostDto f2 = new FilePostDto();
        f2.setId(2);

        assertNotEquals(f1, f2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        FilePostDto f1 = new FilePostDto();
        f1.setId(1);
        f1.setFile(null);

        FilePostDto f2 = new FilePostDto();
        f2.setId(1);
        f2.setFile(null);

        assertEquals(f1, f2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        FilePostDto f1 = new FilePostDto();
        f1.setId(1);
        f1.setFile(null);

        FilePostDto f2 = new FilePostDto();
        f2.setId(1);
        f2.setFile(null);

        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void testToString() {
        FilePostDto original = new FilePostDto();
        original.setId(1);
        original.setFile(null);

        String expected = "FilePostDto{" +
                "id=" + original.getId() +
                ", multipartFile=" + original.getFile() +
                '}';
        assertEquals(expected, original.toString());
    }
}