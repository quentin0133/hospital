package fr.cfa.hospital.core.exception;

public class ProvidedSaveIdException extends RuntimeException {
    public ProvidedSaveIdException() {
        super("Cannot provide ID when saving");
    }
}
