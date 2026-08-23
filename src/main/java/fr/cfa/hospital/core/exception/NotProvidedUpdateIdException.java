package fr.cfa.hospital.core.exception;

public class NotProvidedUpdateIdException extends RuntimeException {
    public NotProvidedUpdateIdException() {
        super("Need to provide ID when updating");
    }
}
