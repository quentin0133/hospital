package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

/**
 * Service contract for managing medical consultations, associated prescriptions,
 * and attachment uploads.
 */
public interface ConsultationService {

    /**
     * Retrieves a paginated list of all consultations.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of consultation DTOs
     */
    Page<ConsultationDto> findAll(Pageable pageable);

    /**
     * Creates and persists a new consultation.
     *
     * @param dto the payload DTO containing consultation details
     * @return the created consultation as a DTO
     */
    ConsultationDto save(ConsultationPostDto dto);

    /**
     * Updates an existing consultation.
     *
     * @param dto the payload DTO containing updated consultation details
     * @return the updated consultation as a DTO
     */
    ConsultationDto update(ConsultationPostDto dto);

    /**
     * Deletes a consultation by its unique identifier.
     *
     * @param id the unique identifier of the consultation to remove
     */
    void deleteById(String id);

    /**
     * Finds a consultation by its unique identifier.
     *
     * @param id the unique identifier of the consultation
     * @return the corresponding consultation DTO
     */
    ConsultationDto findById(String id);

    /**
     * Retrieves a paginated list of consultations associated with a specific patient.
     *
     * @param patientId the unique identifier of the patient
     * @param pageable  pagination and sorting parameters
     * @return a page of consultation DTOs for the patient
     */
    Page<ConsultationDto> findByPatientId(String patientId, Pageable pageable);

    /**
     * Uploads and attaches a file to an existing consultation.
     *
     * @param dto the payload DTO containing the multipart file and target consultation ID
     * @return the uploaded file metadata as a DTO
     * @throws IOException if an error occurs while writing the file to storage
     */
    FileDto uploadFiles(FilePostDto dto) throws IOException;

    /**
     * Adds a prescribed medication with a defined quantity to an existing consultation.
     *
     * @param idConsultation the unique identifier of the consultation
     * @param idMedication   the unique identifier of the medication
     * @param quantity       the quantity prescribed
     * @return the updated consultation DTO
     */
    ConsultationDto addPrescription(String idConsultation, String idMedication, int quantity);

    /**
     * Removes a prescribed medication from an existing consultation.
     *
     * @param idConsultation the unique identifier of the consultation
     * @param idMedication   the unique identifier of the medication to remove
     * @return the updated consultation DTO
     */
    ConsultationDto removePrescription(String idConsultation, String idMedication);
}