package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationGetDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.generic.GenericMapperAbstract;
import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.doctor.DoctorRepository;
import fr.cfa.hospital.medication.Medication;
import fr.cfa.hospital.medication.MedicationMapper;
import fr.cfa.hospital.medication.MedicationRepository;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.patient.PatientRepository;
import fr.cfa.hospital.relations.medication.consultation.MedicationConsultation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ConsultationMapper extends GenericMapperAbstract<Consultation, ConsultationGetDto, ConsultationPostDto> {
    private MedicationMapper medicationMapper;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private MedicationRepository medicationRepository;

    @Autowired
    public void setMedicationMapper(MedicationMapper medicationMapper) {
        this.medicationMapper = medicationMapper;
    }

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    @Mapping(source = "medicationConsultations", target = "medications")
    public abstract ConsultationGetDto toDto(Consultation entity);

    @Override
    @Mapping(source = "idDoctor", target = "doctor")
    public abstract Consultation toEntity(ConsultationPostDto dto);

    protected List<MedicationWithoutConsultationDto> map(List<MedicationConsultation> medicationConsultations) {
        if (medicationConsultations == null) return new ArrayList<>();
        return medicationConsultations.stream()
            .map(MedicationConsultation::getMedication)
            .map(medicationMapper::toDtoWithoutConsultation)
            .toList();
    }

    protected Doctor mapDoctor(long idDoctor) {
        return doctorRepository.findById(idDoctor).orElseThrow(() -> new ResourceNotFoundException("Doctor", idDoctor));
    }

    protected Patient mapPatient(long idPatient) {
        return patientRepository.findById(idPatient).orElseThrow(() -> new ResourceNotFoundException("Patient", idPatient));
    }

    protected List<Medication> mapMedication(List<Long> idsMedication) {
        return medicationRepository.findAllById(idsMedication);
    }
}
