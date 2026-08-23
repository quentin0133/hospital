package fr.cfa.hospital.prescription.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * The type Prescription light dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionLightDto {
    private long id;
    private long medicationId;
    private long consultationId;
    private int quantity;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PrescriptionLightDto that = (PrescriptionLightDto) object;
        return id == that.id && medicationId == that.medicationId && consultationId == that.consultationId && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicationId, consultationId, quantity);
    }

    @Override
    public String toString() {
        return "PrescriptionLightDto{" +
            "id=" + getId() +
            ", medicationId=" + getMedicationId() +
            ", consultationId=" + getConsultationId() +
            ", quantity=" + getQuantity() +
            '}';
    }
}
