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
        assertNull(dto.getId());
        assertNull(dto.getUsername());
        assertNull(dto.getPassword());
    }

    @Test
    void testConstructorFull_validInput() {
        User dto = new User("1", "user", "pwd", "ROLE_ADMIN", false);

        assertEquals("1", dto.getId());
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
    void testEquals_differentId() {
        User c1 = new User();
        c1.setId("1");

        User c2 = new User();
        c2.setId("1");

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_differentUsername() {
        User c1 = new User();
        c1.setId("1");
        c1.setUsername("1");

        User c2 = new User();
        c2.setId("1");
        c2.setUsername("2");

        assertEquals(c1, c2);
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
        c1.setId("6a8f087b903bb0625fcf3995");
        c1.setUsername("user");
        c1.setPassword("pwd");

        User c2 = new User();
        c2.setId("6a8f087b903bb0625fcf3995");
        c2.setUsername("user");
        c2.setPassword("pwd");

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        User c1 = new User();
        c1.setId("6a8f087b903bb0625fcf3995");
        c1.setUsername("user");
        c1.setPassword("pwd");

        User c2 = new User();
        c2.setId("6a8f087b903bb0625fcf3995");
        c2.setUsername("user");
        c2.setPassword("pwd");

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        User original = new User();
        original.setId("1");
        original.setUsername("user");
        original.setPassword("pwd");
        original.setRole("ROLE_ADMIN");
        original.setMustChangePassword(true);

        String expected = "User{" +
            "id='" + original.getId() + '\'' +
            ", username='" + original.getUsername() + '\'' +
            ", password='" + original.getPassword() + '\'' +
            ", role='" + original.getRole() + '\'' +
            ", mustChangePassword=" + original.isMustChangePassword() +
            '}';
        assertEquals(expected, original.toString());
    }
}