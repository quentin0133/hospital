package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.generic.GenericMapperAbstract;
import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.doctor.DoctorRepository;
import fr.cfa.hospital.file.FileMapper;
import fr.cfa.hospital.medication.MedicationMapper;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.patient.PatientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MedicationMapper.class, FileMapper.class})
public abstract class ConsultationMapper extends GenericMapperAbstract<Consultation, ConsultationDto, ConsultationPostDto> {
    @Autowired
    protected DoctorRepository doctorRepository;

    @Autowired
    protected PatientRepository patientRepository;

    @Override
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "patient", source = "patientId")
    @Mapping(target = "file", ignore = true)
    public abstract Consultation toEntity(ConsultationPostDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", source = "version")
    @Mapping(target = "prescriptions", ignore = true)
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "doctor", source = "doctorId")
    @Mapping(target = "patient", source = "patientId")
    public abstract Consultation updateEntityFromDto(ConsultationPostDto dto, @MappingTarget Consultation consultation);

    public Doctor mapDoctor(String idDoctor) {
        return doctorRepository.findById(idDoctor)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", idDoctor));
    }

    public Patient mapPatient(String idPatient) {
        return patientRepository.findById(idPatient)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", idPatient));
    }
}
