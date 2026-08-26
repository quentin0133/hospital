package fr.cfa.hospital.prescription;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.medication.Medication;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Prescription.
 */
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @ColumnDefault("0")
    private int version;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    private int quantity;

    public Prescription() {
    }

    public Prescription(Long id, int version, Medication medication, Consultation consultation, int quantity) {
        this.id = id;
        this.version = version;
        this.medication = medication;
        this.consultation = consultation;
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
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", version=" + version +
                ", medication=" + medication +
                ", consultation=" + consultation +
                ", quantity=" + quantity +
                '}';
    }
}
