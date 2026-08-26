package fr.cfa.hospital.prescription;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.consultation.ConsultationRepository;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.generic.GenericMapperAbstract;
import fr.cfa.hospital.medication.Medication;
import fr.cfa.hospital.medication.MedicationRepository;
import fr.cfa.hospital.prescription.dtos.PrescriptionDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The interface Prescription mapper.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PrescriptionMapper extends GenericMapperAbstract<Prescription, PrescriptionDto, PrescriptionPostDto> {
    @Autowired
    protected MedicationRepository medicationRepository;

    @Autowired
    protected ConsultationRepository consultationRepository;

    /**
     * To dto prescription light dto.
     *
     * @param prescription the prescription
     * @return the prescription light dto
     */
    @Override
    @Mapping(source = "medication.id", target = "medicationId")
    @Mapping(source = "consultation.id", target = "consultationId")
    public abstract PrescriptionDto toDto(Prescription prescription);

    /**
     * To entity prescription.
     *
     * @param dto the request
     * @return the prescription
     */
    @Override
    @Mapping(target = "medication", source = "medicationId")
    @Mapping(target = "consultation", source = "consultationId")
    public abstract Prescription toEntity(PrescriptionPostDto dto);

    public Medication mapMedication(Long id) {
        if (id == null) {
            return null;
        }
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", id));
    }

    public Consultation mapConsultation(Long id) {
        if (id == null) {
            return null;
        }
        return consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", id));
    }
}
