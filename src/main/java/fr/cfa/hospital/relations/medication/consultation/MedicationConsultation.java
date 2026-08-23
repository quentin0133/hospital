package fr.cfa.hospital.relations.medication.consultation;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.medication.Medication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medication_consultation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicationConsultation {
    @EmbeddedId
    private MedicationConsultationId id = new MedicationConsultationId();

    @ManyToOne
    @MapsId("medicationId")
    private Medication medication;

    @ManyToOne
    @MapsId("consultationId")
    private Consultation consultation;

    @Column(nullable = false)
    private int quantity;
}
