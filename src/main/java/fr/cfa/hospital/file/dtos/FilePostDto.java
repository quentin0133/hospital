package fr.cfa.hospital.file.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FilePostDto {
    private String idConsultation;

    private MultipartFile file;

    public FilePostDto() {
    }

    public FilePostDto(String idConsultation, MultipartFile file) {
        this.idConsultation = idConsultation;
        this.file = file;
    }

    public String getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(String idConsultation) {
        this.idConsultation = idConsultation;
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
        return idConsultation != null && Objects.equals(idConsultation, that.idConsultation);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "FilePostDto{" +
                "idConsultation=" + idConsultation +
                ", multipartFile=" + file +
                '}';
    }
}
