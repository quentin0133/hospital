package fr.cfa.hospital.prescription.dtos;

import java.util.Objects;

/**
 * The type Prescription post dto.
 */
public class PrescriptionPostDto {
    private Long id;

    private int version;

    private long medicationId;

    private long consultationId;

    private int quantity;

    public PrescriptionPostDto() {
    }

    public PrescriptionPostDto(Long id, int version, long medicationId, long consultationId, int quantity) {
        this.id = id;
        this.version = version;
        this.medicationId = medicationId;
        this.consultationId = consultationId;
        this.quantity = quantity;
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

    public long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(long medicationId) {
        this.medicationId = medicationId;
    }

    public long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(long consultationId) {
        this.consultationId = consultationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PrescriptionPostDto that = (PrescriptionPostDto) object;
        return version == that.version && medicationId == that.medicationId && consultationId == that.consultationId && quantity == that.quantity && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PrescriptionPostDto{" +
                "id=" + id +
                ", version=" + version +
                ", medicationId=" + medicationId +
                ", consultationId=" + consultationId +
                ", quantity=" + quantity +
                '}';
    }
}
