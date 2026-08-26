package fr.cfa.hospital.medication;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.core.generic.GenericMapper;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import fr.cfa.hospital.prescription.Prescription;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicationMapper extends GenericMapper<Medication, MedicationDto, MedicationPostDto> {
    @Override
    @Mapping(target = "prescriptions", source = "..")
    Medication toEntity(MedicationPostDto dto);

    default List<Prescription> mapConsultationIdsToPrescriptions(MedicationPostDto dto) {
        if (dto == null || dto.getIdsConsultations() == null) {
            return new ArrayList<>();
        }

        return dto.getIdsConsultations().stream().map(consultationId -> {
            Prescription prescription = new Prescription();

            Consultation consultation = new Consultation();
            consultation.setId(consultationId);

            prescription.setConsultation(consultation);
            prescription.setQuantity(dto.getQuantity());

            return prescription;
        }).collect(Collectors.toList());
    }

    @AfterMapping
    default void linkPrescriptions(@MappingTarget Medication medication) {
        if (medication.getPrescriptions() != null) {
            for (Prescription p : medication.getPrescriptions()) {
                p.setMedication(medication);
            }
        }
    }
}
