package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserMapper;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthServiceImpl authServiceImpl;

    @BeforeEach
    void setUp() {
        authServiceImpl = new AuthServiceImpl(authenticationManager, userMapper);
        ReflectionTestUtils.setField(authServiceImpl, "jwtSecretKey", "token");
    }

    @Test
    void authenticate_validInput() {
        try (MockedStatic<JwtUtils> jwtUtilsMockedStatic = mockStatic(JwtUtils.class)) {
            Authentication authentication = Mockito.mock(Authentication.class);
            UserSecurity userSecurity = new UserSecurity(new User(1, "admin", "admin"));
            LoginDto expected = new LoginDto(new UserDto(1, "admin"), "token");

            jwtUtilsMockedStatic.when(() -> JwtUtils.generateToken(any(UserDetails.class), anyString())).thenReturn("token");
            when(userMapper.toDto(any(User.class))).thenReturn(new UserDto(1, "admin"));
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(userSecurity);
            when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

            LoginDto result = authServiceImpl.authenticate(new LoginCommandDto("admin", "admin"));

            System.out.println(result.getToken());

            assertEquals(expected, result);

            jwtUtilsMockedStatic.verify(() -> JwtUtils.generateToken(any(UserDetails.class), anyString()));
            verify(userMapper).toDto(any(UserSecurity.class));
            verify(authentication).isAuthenticated();
            verify(authentication).getPrincipal();
            verify(authenticationManager).authenticate(any(Authentication.class));
        }
    }

    @Test
    void authenticate_shouldThrowBadCredentialsExceptionWhenBadCredentials() {
        try (MockedStatic<JwtUtils> jwtUtilsMockedStatic = mockStatic(JwtUtils.class)) {
            Authentication authentication = Mockito.mock(Authentication.class);
            LoginCommandDto request = new LoginCommandDto("admin", "admin");

            when(authentication.isAuthenticated()).thenReturn(false);
            when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

            assertThrows(BadCredentialsException.class, () -> authServiceImpl.authenticate(request));

            verify(authentication).isAuthenticated();
            verify(authenticationManager).authenticate(any(Authentication.class));
            verifyNoInteractions(userMapper);
            jwtUtilsMockedStatic.verifyNoInteractions();
        }
    }

    @Test
    void authenticate_shouldThrowIllegalStateExceptionExceptionWhenPrincipalIsNotInstanceOfUserSecurity() {
        try (MockedStatic<JwtUtils> jwtUtilsMockedStatic = mockStatic(JwtUtils.class)) {
            Authentication authentication = Mockito.mock(Authentication.class);
            LoginCommandDto request = new LoginCommandDto("admin", "admin");

            when(authentication.getPrincipal()).thenReturn(new Object());
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

            assertThrows(IllegalStateException.class, () -> authServiceImpl.authenticate(request));

            verify(authentication).isAuthenticated();
            verify(authentication).getPrincipal();
            verify(authenticationManager).authenticate(any(Authentication.class));
            verifyNoInteractions(userMapper);
            jwtUtilsMockedStatic.verifyNoInteractions();
        }
    }
}