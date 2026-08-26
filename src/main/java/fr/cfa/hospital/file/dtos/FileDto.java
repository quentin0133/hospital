package fr.cfa.hospital.file.dtos;

import java.util.Objects;

public class FileDto {
    private String fileName;

    private String storedFileName;

    public FileDto() {
    }

    public FileDto(String fileName, String storedFileName) {
        this.fileName = fileName;
        this.storedFileName = storedFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        FileDto that = (FileDto) object;
        return Objects.equals(fileName, that.fileName) && Objects.equals(storedFileName, that.storedFileName);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "fileName='" + fileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                '}';
    }
}
