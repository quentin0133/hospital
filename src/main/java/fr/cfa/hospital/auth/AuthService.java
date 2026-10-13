package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import org.springframework.security.core.AuthenticationException;

/**
 * Service contract defining authentication operations for the application.
 * Handles credential verification and issuance of security tokens.
 */
public interface AuthService {

  /**
   * Authenticates a user based on provided credentials and issues an access token.
   *
   * @param login the payload containing credentials (username and password)
   * @return a {@link LoginDto} containing the generated JWT token and associated user details
   * @throws AuthenticationException if the credentials are invalid, bad, or the user is disabled
   */
  LoginDto authenticate(LoginPostDto login) throws AuthenticationException;
}