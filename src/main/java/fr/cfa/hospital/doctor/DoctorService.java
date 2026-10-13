package fr.cfa.hospital.doctor;

import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.doctor.dtos.DoctorPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service contract for managing doctor profiles, search queries,
 * and user account associations.
 */
public interface DoctorService {

    /**
     * Retrieves a paginated list of all registered doctors.
     *
     * @param pageable pagination and sorting parameters
     * @return a page of doctor DTOs
     */
    Page<DoctorDto> findAll(Pageable pageable);

    /**
     * Retrieves an unpaged list of all registered doctors.
     *
     * @return a list of all doctor DTOs
     */
    List<DoctorDto> findAll();

    /**
     * Searches for doctors matching the specified search term with pagination.
     *
     * @param search   the search query term
     * @param pageable pagination and sorting parameters
     * @return a page of matching doctor DTOs
     */
    Page<DoctorDto> findByName(String search, Pageable pageable);

    /**
     * Creates and persists a new doctor profile.
     *
     * @param dto the payload DTO containing doctor profile details
     * @return the created doctor as a DTO
     */
    DoctorDto save(DoctorPostDto dto);

    /**
     * Updates an existing doctor profile.
     *
     * @param dto the payload DTO containing updated doctor details
     * @return the updated doctor as a DTO
     */
    DoctorDto update(DoctorPostDto dto);

    /**
     * Deletes a doctor profile by its unique identifier.
     *
     * @param id the unique identifier of the doctor to remove
     */
    void deleteById(String id);

    /**
     * Finds a doctor profile by its unique identifier.
     *
     * @param id the unique identifier of the doctor
     * @return the corresponding doctor DTO
     */
    DoctorDto findById(String id);

    /**
     * Finds doctors whose names contain the given substring, with pagination.
     *
     * @param name     the partial name string to search for
     * @param pageable pagination and sorting parameters
     * @return a page of matching doctor DTOs
     */
    Page<DoctorDto> findByNameContains(String name, Pageable pageable);

    /**
     * Links a user authentication account to an existing doctor profile.
     *
     * @param idDoctor the unique identifier of the doctor
     * @param id       the unique identifier of the user account to associate
     */
    void linkUser(String idDoctor, String id);

    /**
     * Removes the association between a doctor profile and its user account.
     *
     * @param idDoctor the unique identifier of the doctor profile to unlink
     */
    void unlinkUser(String idDoctor);
}