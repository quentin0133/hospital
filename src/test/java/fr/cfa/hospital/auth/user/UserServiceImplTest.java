package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.UserSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername_validInput() {
        User user = new User();
        UserDetails expected = new UserSecurity(user);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails result = userServiceImpl.loadUserByUsername(anyString());

        assertEquals(expected.getUsername(), result.getUsername());
        assertEquals(expected.getAuthorities(), result.getAuthorities());
        assertEquals(expected.getPassword(), result.getPassword());

        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void loadUserByUsername_shouldReturnThrowsUsernameNotFoundExceptionWhenUsernameNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("Michel"));

        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void loadUserByUsername_throwsUsernameNotFoundExceptionWhenNull() {
        when(userRepository.findByUsername(isNull())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername(null));

        verify(userRepository).findByUsername(isNull());
    }
}