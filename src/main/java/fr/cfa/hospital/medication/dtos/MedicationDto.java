package fr.cfa.hospital.medication.dtos;

import java.util.Objects;

public class MedicationDto {
    private long id;

    private int version;

    private String label;

    public MedicationDto() {
    }

    public MedicationDto(long id, int version, String label) {
        this.id = id;
        this.version = version;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return id == that.id && version == that.version && Objects.equals(label, that.label);
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
