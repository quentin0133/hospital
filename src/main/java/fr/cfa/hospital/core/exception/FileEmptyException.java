package fr.cfa.hospital.core.exception;

public class FileEmptyException extends RuntimeException {
    public FileEmptyException() {
        super("File is null or empty");
    }
}
