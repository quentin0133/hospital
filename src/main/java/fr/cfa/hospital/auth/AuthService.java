package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
  LoginDto authenticate(LoginPostDto login) throws AuthenticationException;
}
