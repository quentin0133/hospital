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
class LoginPostDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        LoginPostDto dto = new LoginPostDto();

        assertNotNull(dto);
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }

    @Test
    void testConstructorFull_validInput() {
        LoginPostDto dto = new LoginPostDto("usr", "pwd");

        assertEquals("usr", dto.getUsername());
        assertEquals("pwd", dto.getPassword());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        LoginPostDto original = new LoginPostDto("usr", "pwd");

        String json = objectMapper.writeValueAsString(original);
        LoginPostDto result = objectMapper.readValue(json, LoginPostDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        LoginPostDto original = new LoginPostDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new LoginPostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new LoginPostDto());
    }

    @Test
    void testEquals_differentUsername() {
        LoginPostDto c1 = new LoginPostDto();
        c1.setUsername("1");

        LoginPostDto c2 = new LoginPostDto();
        c2.setUsername("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_differentPassword() {
        LoginPostDto c1 = new LoginPostDto();
        c1.setPassword("1");

        LoginPostDto c2 = new LoginPostDto();
        c2.setPassword("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        LoginPostDto c1 = new LoginPostDto();
        c1.setUsername("usr");
        c1.setPassword("pwd");

        LoginPostDto c2 = new LoginPostDto();
        c2.setUsername("usr");
        c2.setPassword("pwd");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        LoginPostDto c1 = new LoginPostDto();
        c1.setUsername("usr");
        c1.setPassword("pwd");

        LoginPostDto c2 = new LoginPostDto();
        c2.setUsername("usr");
        c2.setPassword("pwd");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        LoginPostDto original = new LoginPostDto();
        original.setUsername("usr");
        original.setPassword("pwd");

        String expected = "LoginPostDto{" +
                "username='" + original.getUsername() + '\'' +
                ", password='" + original.getPassword() + '\'' +
                '}';
        assertEquals(expected, original.toString());
    }
}