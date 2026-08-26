package fr.cfa.hospital.patient.dtos;

import java.util.Objects;

public class PatientPostDto {
    private Long id;

    private int version;

    private String name;

    public PatientPostDto() {
    }

    public PatientPostDto(Long id, int version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PatientPostDto that = (PatientPostDto) object;
        return version == that.version && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PatientPostDto{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}
