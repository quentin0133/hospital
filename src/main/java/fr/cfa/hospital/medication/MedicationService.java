package fr.cfa.hospital.medication;

import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service contract for managing medications in the catalog.
 */
public interface MedicationService {

    /**
     * Retrieves a list of all medications.
     *
     * @return a list of medication DTOs
     */
    List<MedicationDto> findAll();

    /**
     * Finds a medication by its unique identifier.
     *
     * @param id the unique identifier of the medication
     * @return the corresponding medication DTO
     */
    MedicationDto findById(String id);

    /**
     * Creates and persists a new medication.
     *
     * @param dto the payload DTO containing medication details
     * @return the created medication as a DTO
     */
    MedicationDto save(MedicationPostDto dto);

    /**
     * Updates an existing medication.
     *
     * @param dto the payload DTO containing updated medication details
     * @return the updated medication as a DTO
     */
    MedicationDto update(MedicationPostDto dto);

    /**
     * Deletes a medication by its unique identifier.
     *
     * @param id the unique identifier of the medication to remove
     */
    void deleteById(String id);
}