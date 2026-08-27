package fr.cfa.hospital.core.exception;

public class AccountExistsException extends RuntimeException {
    public AccountExistsException() {
        super("Account already exists");
    }
}
