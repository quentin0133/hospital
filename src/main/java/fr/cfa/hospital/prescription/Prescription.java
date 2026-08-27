package fr.cfa.hospital.prescription;

import fr.cfa.hospital.medication.Medication;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * The type Prescription.
 */
public class Prescription {
    private int quantity;

    @DocumentReference
    private Medication medication;

    public Prescription() {
    }

    public Prescription(Medication medication, int quantity) {
        this.medication = medication;
        this.quantity = quantity;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
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
        Prescription that = (Prescription) object;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Prescription{" +
                ", medication=" + medication +
                ", quantity=" + quantity +
                '}';
    }
}
