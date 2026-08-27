package fr.cfa.hospital.patient.dtos;

import java.util.Objects;

/**
 * The type Patient dto.
 */
public class PatientDto {
    private String id;

    private int version;

    private String name;

    private String idUser;

    public PatientDto() {
    }

    public PatientDto(String id, int version, String name, String idUser) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.idUser = idUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PatientDto dto = (PatientDto) object;
        return id != null && Objects.equals(id, dto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PatientDto{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}
