package fr.cfa.hospital.medication.dtos;

import java.util.Objects;

public class MedicationDto {
    private String id;

    private int version;

    private String label;

    public MedicationDto() {
    }

    public MedicationDto(String id, int version, String label) {
        this.id = id;
        this.version = version;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        MedicationDto that = (MedicationDto) object;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "MedicationDto{" +
                "id=" + id +
                ", version=" + version +
                ", label='" + label + '\'' +
                '}';
    }
}
