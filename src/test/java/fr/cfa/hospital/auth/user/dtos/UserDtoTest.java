package fr.cfa.hospital.auth.user.dtos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserDtoTest {
    @Test
    void testConstructorEmpty_validInput() {
        UserDto dto = new UserDto();

        assertNotNull(dto);
        assertEquals(0, dto.getId());
        assertNull(dto.getUsername());
    }

    @Test
    void testConstructorFull_validInput() {
        UserDto dto = new UserDto(1, "michel");

        assertEquals(1, dto.getId());
        assertEquals("michel", dto.getUsername());
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        UserDto original = new UserDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new UserDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new UserDto());
    }

    @Test
    void testEquals_differentId() {
        UserDto c1 = new UserDto();
        c1.setId(1);

        UserDto c2 = new UserDto();
        c2.setId(2);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentUsername() {
        UserDto c1 = new UserDto();
        c1.setUsername("1");

        UserDto c2 = new UserDto();
        c2.setUsername("2");

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        UserDto c1 = new UserDto();
        c1.setId(1);
        c1.setUsername("michel");

        UserDto c2 = new UserDto();
        c2.setId(1);
        c2.setUsername("michel");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        UserDto c1 = new UserDto();
        c1.setId(1);
        c1.setUsername("michel");

        UserDto c2 = new UserDto();
        c2.setId(1);
        c2.setUsername("michel");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        UserDto user = new UserDto();
        user.setId(1);
        user.setUsername("michel");

        String expected = "UserDto{" +
            "id=" + user.getId() +
            ", username='" + user.getUsername() + '\'' +
            '}';
        assertEquals(expected, user.toString());
    }
}