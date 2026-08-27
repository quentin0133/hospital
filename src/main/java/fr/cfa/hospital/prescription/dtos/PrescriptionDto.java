package fr.cfa.hospital.prescription.dtos;

import fr.cfa.hospital.medication.dtos.MedicationDto;

import java.util.Objects;

/**
 * The type Prescription dto.
 */
public class PrescriptionDto {
    private MedicationDto medication;

    private int quantity;

    public PrescriptionDto() {
    }

    public PrescriptionDto(MedicationDto medication, int quantity) {
        this.medication = medication;
        this.quantity = quantity;
    }

    public MedicationDto getMedication() {
        return medication;
    }

    public void setMedication(MedicationDto medication) {
        this.medication = medication;
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
        PrescriptionDto that = (PrescriptionDto) object;
        return quantity == that.quantity && Objects.equals(medication, that.medication);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "PrescriptionDto{" +
                "medication=" + medication +
                ", quantity=" + quantity +
                '}';
    }
}
