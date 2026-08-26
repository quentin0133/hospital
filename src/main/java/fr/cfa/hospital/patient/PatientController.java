package fr.cfa.hospital.patient;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.patient.dtos.PatientDto;
import fr.cfa.hospital.patient.dtos.PatientPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Patient controller.
 */
@Tag(name = "Patients", description = "API to manage patients within the hospital")
public interface PatientController {

    @Operation(summary = "Get all patients", description = "Retrieves a paginated list of all patients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<PatientDto>> findAll(
            @Parameter(description = "Pagination parameters") Pageable pageable
    );

    @Operation(summary = "Find patients by name", description = "Retrieves a paginated list of patients matching the given name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<PatientDto>> findAllByName(
            @PathVariable @Parameter(description = "Name of the patient to search for", required = true) String name,
            @Parameter(description = "Pagination parameters") Pageable pageable
    );

    @Operation(summary = "Get patient by ID", description = "Retrieves a specific patient by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patient"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<PatientDto> findById(
            @PathVariable @Parameter(description = "Patient ID", required = true) long id
    );

    @Operation(summary = "Create a patient", description = "Creates a new patient in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<PatientDto> save(
            @RequestBody @Parameter(description = "Patient details to create", required = true) PatientPostDto dto
    );

    @Operation(summary = "Update a patient", description = "Updates an existing patient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<PatientDto> update(
            @RequestBody @Parameter(description = "Patient details to update", required = true) PatientPostDto dto
    ) throws ResourceNotFoundException;

    @Operation(summary = "Delete a patient", description = "Deletes an existing patient by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    ResponseEntity<Void> deleteById(
            @PathVariable @Parameter(description = "Patient ID", required = true) long id
    ) throws ResourceNotFoundException;
}