package fr.cfa.hospital.prescription.dtos;

/**
 * The type Prescription light dto.
 */
public class PrescriptionDto {
    private long id;

    private int version;

    private long medicationId;

    private long consultationId;

    private int quantity;

    public PrescriptionDto() {
    }

    public PrescriptionDto(long id, int version, long medicationId, long consultationId, int quantity) {
        this.id = id;
        this.version = version;
        this.medicationId = medicationId;
        this.consultationId = consultationId;
        this.quantity = quantity;
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
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PrescriptionDto that = (PrescriptionDto) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PrescriptionDto{" +
                "id=" + id +
                ", version=" + version +
                ", medicationId=" + medicationId +
                ", consultationId=" + consultationId +
                ", quantity=" + quantity +
                '}';
    }
}
