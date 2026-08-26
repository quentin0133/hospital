package fr.cfa.hospital.prescription;

import fr.cfa.hospital.prescription.dtos.PrescriptionDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionPostDto;

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
    PrescriptionDto update(PrescriptionPostDto dto);
}
