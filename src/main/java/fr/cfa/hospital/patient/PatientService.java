package fr.cfa.hospital.patient;

import fr.cfa.hospital.core.exception.AccountExistsException;
import fr.cfa.hospital.core.exception.AccountNotFoundException;
import fr.cfa.hospital.patient.dtos.PatientDto;
import fr.cfa.hospital.patient.dtos.PatientPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service contract for managing patient records, search queries,
 * and user account associations.
 */
public interface PatientService {

    /**
     * Retrieves a paginated list of all registered patients.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of patient DTOs
     */
    Page<PatientDto> findAll(Pageable pageable);

    /**
     * Searches for patients matching the given name with pagination.
     *
     * @param name     the search query name
     * @param pageable pagination and sorting parameters
     * @return a page of matching patient DTOs
     */
    Page<PatientDto> findByName(String name, Pageable pageable);

    /**
     * Finds a patient record by their unique identifier.
     *
     * @param id the unique identifier of the patient
     * @return the corresponding patient DTO
     */
    PatientDto findById(String id);

    /**
     * Creates and persists a new patient record.
     *
     * @param dto the payload DTO containing patient details
     * @return the created patient as a DTO
     */
    PatientDto save(PatientPostDto dto);

    /**
     * Updates an existing patient record.
     *
     * @param dto the payload DTO containing updated patient details
     * @return the updated patient as a DTO
     */
    PatientDto update(PatientPostDto dto);

    /**
     * Deletes a patient record by its unique identifier.
     *
     * @param id the unique identifier of the patient to remove
     */
    void deleteById(String id);

    /**
     * Links a user authentication account to an existing patient record.
     *
     * @param idPatient the unique identifier of the patient
     * @param idUser    the unique identifier of the user account to associate
     * @throws AccountExistsException if the patient already has an associated user account
     */
    void linkUser(String idPatient, String idUser) throws AccountExistsException;

    /**
     * Removes the association between a patient record and their user account.
     *
     * @param idPatient the unique identifier of the patient record to unlink
     * @throws AccountNotFoundException if no user account is linked to the patient
     */
    void unlinkUser(String idPatient) throws AccountNotFoundException;
}