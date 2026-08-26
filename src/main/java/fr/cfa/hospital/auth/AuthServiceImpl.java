package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import fr.cfa.hospital.auth.user.UserMapper;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.core.tools.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final AuthMapper mapper;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, AuthMapper mapper, UserMapper userMapper, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginDto authenticate(LoginPostDto request) throws AuthenticationException {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException("The principal is not an instance of UserSecurity");
        }

        UserDto userDto = userMapper.toDto(userSecurity.getUser());
        String token = jwtUtils.generateToken(userSecurity);

        return mapper.toLoginResponse(userDto, token);
    }
}
