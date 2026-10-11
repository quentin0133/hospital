package fr.cfa.hospital.medication.dtos;

public class MedicationPostDto {
    private String id;

    private int version;

    private String label;

    public MedicationPostDto() {
    }

    public MedicationPostDto(String id, int version, String label) {
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
    public String toString() {
        return "MedicationPostDto{" +
                "id=" + id +
                ", version=" + version +
                ", label='" + label + '\'' +
                '}';
    }
}
