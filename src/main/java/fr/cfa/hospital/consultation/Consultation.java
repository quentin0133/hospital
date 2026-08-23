package fr.cfa.hospital.consultation;

import fr.cfa.hospital.core.generic.BaseEntity;
import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.relations.medication.consultation.MedicationConsultation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {
    private long number;

    private LocalDate date;

    @OneToMany(mappedBy = "consultation")
    private List<MedicationConsultation> medicationConsultations = new ArrayList<>();

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;
}
