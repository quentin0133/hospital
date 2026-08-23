package fr.cfa.hospital.relations.medication.consultation;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@Getter
public class MedicationConsultationId implements Serializable {
    private Long medicationId;
    private Long consultationId;
}
