package fr.cfa.hospital.prescription;

import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;

/**
 * The interface Prescription mapper.
 */
public interface PrescriptionMapper {
    /**
     * To dto prescription light dto.
     *
     * @param prescription the prescription
     * @return the prescription light dto
     */
    PrescriptionLightDto toDto(Prescription prescription);

    /**
     * To entity prescription.
     *
     * @param prescription the prescription
     * @return the prescription
     */
    Prescription toEntity(PrescriptionCommandDto prescription);
}
