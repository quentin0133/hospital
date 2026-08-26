package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.user.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserMapperImplTest {
    private UserMapper userMapperImpl;

    @BeforeEach
    void setUp() {
        userMapperImpl = new UserMapperImpl();
    }

    @Test
    void toDto_validInput() {
        UserDto expected = new UserDto();
        User user = new User();
        user.setId(0L);

        UserDto result = userMapperImpl.toDto(user);

        assertEquals(expected, result);
    }

    @Test
    void toDto_inputNull() {
        assertNull(userMapperImpl.toDto(null));
    }
}