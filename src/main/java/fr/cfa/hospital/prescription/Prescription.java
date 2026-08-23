package fr.cfa.hospital.prescription;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.medication.Medication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Prescription.
 */
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "Code")
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "Numero")
    private Consultation consultation;

    @Column(name = "NB_prises")
    private int quantity;

    public Prescription() {
    }

    public Prescription(int id, Medication medication, Consultation consultation, int quantity) {
        this.id = id;
        this.medication = medication;
        this.consultation = consultation;
        this.quantity = quantity;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return id == that.id &&
            quantity == that.quantity &&
            Objects.equals(medication, that.medication) &&
            Objects.equals(consultation, that.consultation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medication, consultation, quantity);
    }

    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + id +
            ", quantity=" + quantity +
            '}';
    }
}
