package fr.cfa.hospital.prescription.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * The type Prescription command dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionCommandDto {
    private Long id;

    private long medicationId;

    private long consultationId;

    private int quantity;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PrescriptionCommandDto that = (PrescriptionCommandDto) object;
        return medicationId == that.medicationId && consultationId == that.consultationId && quantity == that.quantity && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicationId, consultationId, quantity);
    }

    @Override
    public String toString() {
        return "PrescriptionPostUpdateDto{" +
            "id=" + getId() +
            ", medicationId=" + getMedicationId() +
            ", consultationId=" + getConsultationId() +
            ", quantity=" + getQuantity() +
            '}';
    }
}
