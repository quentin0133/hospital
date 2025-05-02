package fr.cfa.hospital.core.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String label, long id) {
        super("%s not found with id %d".formatted(label, id));
    }

    public ResourceNotFoundException(String label, String id) {
        super("%s not found with id %s".formatted(label, id));
    }
}
