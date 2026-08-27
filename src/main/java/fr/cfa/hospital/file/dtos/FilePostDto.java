package fr.cfa.hospital.file.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FilePostDto {
    private String id;

    private MultipartFile file;

    public FilePostDto() {
    }

    public FilePostDto(String id, MultipartFile file) {
        this.id = id;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return id != null && Objects.equals(id, that.id);
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
