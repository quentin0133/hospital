package fr.cfa.hospital.auth;

import fr.quentin.portfolio.portfolioback.auth.dtos.LoginDto;
import fr.quentin.portfolio.portfolioback.auth.dtos.LoginResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
  private final AuthMapper mapper;
  private final AuthenticationManager authenticationManager;

  @Override
  public LoginResponseDto authenticate(LoginDto login) throws AuthenticationException {
    Authentication authenticate =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.username(), login.password()));
    if (authenticate.isAuthenticated()) {
      log.info(
          "Successful authentication for user {} at {}",
          login.username(),
          LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
      return mapper.toLoginResponse((UserSecurity) authenticate.getPrincipal());
    }
    throw new BadCredentialsException("Invalid Credentials");
  }
}
