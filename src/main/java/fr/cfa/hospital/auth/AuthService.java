package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginGetDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
  LoginGetDto authenticate(LoginPostDto login) throws AuthenticationException;
}
