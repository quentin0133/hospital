package fr.cfa.hospital.auth.dtos;

import fr.cfa.hospital.auth.user.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class LoginDtoTest {
    @Test
    void testConstructorEmpty_validInput() {
        LoginDto dto = new LoginDto();

        assertNotNull(dto);
        assertNull(dto.getUser());
        assertNull(dto.getToken());
    }

    @Test
    void testConstructorFull_validInput() {
        LoginDto dto = new LoginDto(new UserDto(), "token");

        assertEquals(new UserDto(), dto.getUser());
        assertEquals("token", dto.getToken());
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        LoginDto original = new LoginDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new LoginDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new LoginDto());
    }

    @Test
    void testEquals_differentUser() {
        LoginDto c1 = new LoginDto();
        c1.setUser(new UserDto());

        LoginDto c2 = new LoginDto();
        c2.setUser(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_differentToken() {
        LoginDto c1 = new LoginDto();
        c1.setToken("1");

        LoginDto c2 = new LoginDto();
        c2.setToken("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        LoginDto c1 = new LoginDto();
        c1.setUser(new UserDto());
        c1.setToken("token");

        LoginDto c2 = new LoginDto();
        c2.setUser(new UserDto());
        c2.setToken("token");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        LoginDto c1 = new LoginDto();
        c1.setUser(new UserDto());
        c1.setToken("token");

        LoginDto c2 = new LoginDto();
        c2.setUser(new UserDto());
        c2.setToken("token");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        LoginDto login = new LoginDto(new UserDto(), "token");

        String expected = "LoginQueryDto{" +
            "user=" + login.getUser() +
            '}';
        assertEquals(expected, login.toString());
    }
}