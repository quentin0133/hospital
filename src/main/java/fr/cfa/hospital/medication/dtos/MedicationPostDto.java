package fr.cfa.hospital.medication.dtos;

import java.util.List;

public class MedicationPostDto {
    private Long id;

    private int version;

    private String label;

    private int quantity = 1;

    private List<Long> idsConsultations;

    public MedicationPostDto() {
    }

    public MedicationPostDto(Long id, int version, String label, int quantity, List<Long> idsConsultations) {
        this.id = id;
        this.version = version;
        this.label = label;
        this.quantity = quantity;
        this.idsConsultations = idsConsultations;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Long> getIdsConsultations() {
        return idsConsultations;
    }

    public void setIdsConsultations(List<Long> idsConsultations) {
        this.idsConsultations = idsConsultations;
    }

    @Override
    public String toString() {
        return "MedicationPostDto{" +
                "id=" + id +
                ", version=" + version +
                ", label='" + label + '\'' +
                ", quantity=" + quantity +
                ", idsConsultations=" + idsConsultations +
                '}';
    }
}
