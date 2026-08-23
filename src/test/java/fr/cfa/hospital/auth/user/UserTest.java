package fr.cfa.hospital.auth.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserTest {

    @Test
    void testConstructorEmpty_validInput() {
        User dto = new User();

        assertNotNull(dto);
        assertEquals(0, dto.getId());
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }

    @Test
    void testConstructorFull_validInput() {
        User dto = new User(1, "user", "pwd");

        assertEquals(1, dto.getId());
        assertEquals("user", dto.getUsername());
        assertEquals("pwd", dto.getPassword());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new User());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new User());
    }

    @Test
    void testNotEquals_differentId() {
        User c1 = new User();
        c1.setId(1);

        User c2 = new User();
        c2.setId(2);

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_differentUsername() {
        User c1 = new User();
        c1.setUsername("1");

        User c2 = new User();
        c2.setUsername("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_differentPwd() {
        User c1 = new User();
        c1.setPassword("1");

        User c2 = new User();
        c2.setPassword("2");

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        User c1 = new User();
        c1.setId(1);
        c1.setUsername("user");
        c1.setPassword("pwd");

        User c2 = new User();
        c2.setId(1);
        c2.setUsername("user");
        c2.setPassword("pwd");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        User c1 = new User();
        c1.setId(1);
        c1.setUsername("user");
        c1.setPassword("pwd");

        User c2 = new User();
        c2.setId(1);
        c2.setUsername("user");
        c2.setPassword("pwd");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        User user = new User(1, "user", "pwd");

        String expected = "User{" +
            "id=" + user.getId() +
            ", username='" + user.getUsername() + '\'' +
            '}';
        assertEquals(expected, user.toString());
    }
}