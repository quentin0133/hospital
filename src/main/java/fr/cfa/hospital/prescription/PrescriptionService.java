package fr.cfa.hospital.prescription;

import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;

/**
 * The interface Prescription service.
 */
public interface PrescriptionService {
    /**
     * Update prescription light dto.
     *
     * @param dto the dto
     * @return the prescription light dto
     */
    PrescriptionLightDto update(PrescriptionCommandDto dto);
}
