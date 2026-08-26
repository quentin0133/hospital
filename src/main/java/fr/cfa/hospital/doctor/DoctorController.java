package fr.cfa.hospital.doctor;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.doctor.dtos.DoctorPostDto;
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
 * The interface Doctor controller.
 */
@Tag(name = "Doctors", description = "API to manage doctors within the hospital")
public interface DoctorController {

    @Operation(summary = "Get all doctors", description = "Retrieves a paginated list of all doctors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<DoctorDto>> findAll(
            @Parameter(description = "Pagination parameters") Pageable pageable
    );

    @Operation(summary = "Get doctor by ID", description = "Retrieves a specific doctor by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved doctor"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    ResponseEntity<DoctorDto> findById(
            @PathVariable @Parameter(description = "Doctor ID", required = true) long id
    );

    @Operation(summary = "Find doctors by name", description = "Retrieves a paginated list of doctors matching the given name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<DoctorDto>> findByName(
            @PathVariable @Parameter(description = "Name of the doctor to search for", required = true) String name,
            @Parameter(description = "Pagination parameters") Pageable pageable
    );

    @Operation(summary = "Create a doctor", description = "Creates a new doctor in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<DoctorDto> save(
            @RequestBody @Parameter(description = "Doctor details to create", required = true) DoctorPostDto dto
    );

    @Operation(summary = "Update a doctor", description = "Updates an existing doctor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    ResponseEntity<DoctorDto> update(
            @RequestBody @Parameter(description = "Doctor details to update", required = true) DoctorPostDto dto
    ) throws ResourceNotFoundException;

    @Operation(summary = "Delete a doctor", description = "Deletes an existing doctor by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    ResponseEntity<Void> deleteById(
            @PathVariable @Parameter(description = "Doctor ID", required = true) long id
    ) throws ResourceNotFoundException;
}