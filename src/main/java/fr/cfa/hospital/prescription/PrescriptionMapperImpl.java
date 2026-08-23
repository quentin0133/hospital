package fr.cfa.hospital.prescription;

import fr.cfa.hospital.consultation.ConsultationRepository;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.utils.LogUtils;
import fr.cfa.hospital.medication.MedicationRepository;
import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PrescriptionMapperImpl implements PrescriptionMapper {
    private final MedicationRepository medicationRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    public PrescriptionLightDto toDto(Prescription entity) {
        LogUtils.logEnter(log, entity);

        if (entity == null)
            return null;

        PrescriptionLightDto dto = new PrescriptionLightDto();
        dto.setId(entity.getId());
        dto.setMedicationId(entity.getMedication().getId());
        dto.setConsultationId(entity.getConsultation().getId());
        dto.setQuantity(entity.getQuantity());

        LogUtils.logExit(log, dto);

        return dto;
    }

    @Override
    public Prescription toEntity(PrescriptionCommandDto dto) {
        LogUtils.logEnter(log, dto);

        if (dto == null)
            return null;

        Prescription entity = new Prescription();
        entity.setId(dto.getId());
        entity.setMedication(medicationRepository.findById(dto.getMedicationId())
            .orElseThrow(() -> new ResourceNotFoundException("Medication not found", dto.getMedicationId())));
        entity.setConsultation(consultationRepository.findById(dto.getConsultationId())
            .orElseThrow(() -> new ResourceNotFoundException("Consultation not found", dto.getConsultationId())));
        entity.setQuantity(dto.getQuantity());

        LogUtils.logExit(log, entity);

        return entity;
    }
}
