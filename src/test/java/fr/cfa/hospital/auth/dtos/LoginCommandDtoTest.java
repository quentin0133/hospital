package fr.cfa.hospital.auth.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class LoginCommandDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        LoginCommandDto dto = new LoginCommandDto();

        assertNotNull(dto);
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }

    @Test
    void testConstructorFull_validInput() {
        LoginCommandDto dto = new LoginCommandDto("usr", "pwd");

        assertEquals("usr", dto.getUsername());
        assertEquals("pwd", dto.getPassword());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        LoginCommandDto original = new LoginCommandDto("usr", "pwd");

        String json = objectMapper.writeValueAsString(original);
        LoginCommandDto result = objectMapper.readValue(json, LoginCommandDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        LoginCommandDto original = new LoginCommandDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new LoginCommandDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new LoginCommandDto());
    }

    @Test
    void testEquals_differentUsername() {
        LoginCommandDto c1 = new LoginCommandDto();
        c1.setUsername("1");

        LoginCommandDto c2 = new LoginCommandDto();
        c2.setUsername("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_differentPassword() {
        LoginCommandDto c1 = new LoginCommandDto();
        c1.setPassword("1");

        LoginCommandDto c2 = new LoginCommandDto();
        c2.setPassword("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        LoginCommandDto c1 = new LoginCommandDto();
        c1.setUsername("usr");
        c1.setPassword("pwd");

        LoginCommandDto c2 = new LoginCommandDto();
        c2.setUsername("usr");
        c2.setPassword("pwd");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        LoginCommandDto c1 = new LoginCommandDto();
        c1.setUsername("usr");
        c1.setPassword("pwd");

        LoginCommandDto c2 = new LoginCommandDto();
        c2.setUsername("usr");
        c2.setPassword("pwd");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        LoginCommandDto login = new LoginCommandDto("usr", "pwd");

        String expected = "LoginCommandDto{" +
            "username='" + login.getUsername() + '\'' +
            '}';
        assertEquals(expected, login.toString());
    }
}