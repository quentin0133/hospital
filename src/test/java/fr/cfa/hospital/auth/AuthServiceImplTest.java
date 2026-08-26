package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserMapper;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.core.tools.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    private AuthServiceImpl authServiceImpl;

    @BeforeEach
    void setUp() {
        authServiceImpl = new AuthServiceImpl(authenticationManager, authMapper, userMapper, jwtUtils);
    }

    @Test
    void authenticate_validInput() {
        Authentication authentication = Mockito.mock(Authentication.class);
        UserSecurity userSecurity = new UserSecurity(new User(1L, "test", "test"));
        LoginDto expected = new LoginDto(new UserDto(1, "test"), "token");

        when(jwtUtils.generateToken(any(UserDetails.class))).thenReturn("token");
        when(userMapper.toDto(any(User.class))).thenReturn(new UserDto(1, "test"));
        when(authentication.getPrincipal()).thenReturn(userSecurity);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authMapper.toLoginResponse(any(UserDto.class), anyString())).thenReturn(expected);

        LoginDto result = authServiceImpl.authenticate(new LoginPostDto("test", "test"));

        assertEquals(expected, result);

        verify(jwtUtils).generateToken(any(UserDetails.class));
        verify(userMapper).toDto(any(User.class));
        verify(authentication).getPrincipal();
        verify(authenticationManager).authenticate(any(Authentication.class));
    }

    @Test
    void authenticate_shouldThrowBadCredentialsExceptionWhenPrincipalIsNotInstanceOfUserSecurity() {
        Authentication authentication = Mockito.mock(Authentication.class);
        LoginPostDto request = new LoginPostDto("admin", "admin");

        when(authentication.getPrincipal()).thenReturn(new Object());
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        assertThrows(BadCredentialsException.class, () -> authServiceImpl.authenticate(request));

        verify(authentication).getPrincipal();
        verify(authenticationManager).authenticate(any(Authentication.class));
        verifyNoInteractions(userMapper);
        verifyNoInteractions(jwtUtils);
    }
}