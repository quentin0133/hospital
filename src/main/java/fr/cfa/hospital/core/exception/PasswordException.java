package fr.cfa.hospital.core.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordException extends AuthenticationException {
    public PasswordException() {
        super("Password incorrect");
    }
}
