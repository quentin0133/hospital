package fr.cfa.hospital.medication;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Medication controller.
 */
@Tag(name = "Medications", description = "API to manage medications and prescriptions catalog")
public interface MedicationController {

    @Operation(summary = "Get medication by ID", description = "Retrieves a specific medication by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved medication"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    ResponseEntity<MedicationDto> findById(
            @PathVariable @Parameter(description = "Medication ID", required = true) long id
    );

    @Operation(summary = "Create a medication", description = "Creates a new medication entry in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medication created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<MedicationDto> save(
            @RequestBody @Parameter(description = "Medication details to create", required = true) MedicationPostDto dto
    );

    @Operation(summary = "Update a medication", description = "Updates an existing medication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medication updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    ResponseEntity<MedicationDto> update(
            @RequestBody @Parameter(description = "Medication details to update", required = true) MedicationPostDto dto
    ) throws ResourceNotFoundException;

    @Operation(summary = "Delete a medication", description = "Deletes an existing medication by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medication deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Medication not found")
    })
    ResponseEntity<Void> deleteById(
            @PathVariable @Parameter(description = "Medication ID", required = true) long id
    ) throws ResourceNotFoundException;
}