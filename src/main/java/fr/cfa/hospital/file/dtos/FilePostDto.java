package fr.cfa.hospital.file.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FilePostDto {
    private long id;

    private MultipartFile file;

    public FilePostDto() {
    }

    public FilePostDto(long id, MultipartFile file) {
        this.id = id;
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        FilePostDto that = (FilePostDto) object;
        return id == that.id && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FilePostDto{" +
                "id=" + id +
                ", multipartFile=" + file +
                '}';
    }
}
