package fr.cfa.hospital.file;

import java.util.Objects;

public class File {
    private String fileName;

    private String storedFileName;

    public File() {
    }

    public File(String fileName, String storedFileName) {
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
        File file = (File) object;
        return Objects.equals(fileName, file.fileName) && Objects.equals(storedFileName, file.storedFileName);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                '}';
    }
}
