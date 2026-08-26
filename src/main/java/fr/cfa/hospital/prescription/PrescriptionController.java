package fr.cfa.hospital.prescription;

import fr.cfa.hospital.prescription.dtos.PrescriptionDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * The interface Prescription controller.
 */
@Tag(name = "Prescriptions", description = "API to manage prescriptions (authentication required)")
public interface PrescriptionController {

    /**
     * Update response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @Operation(
        summary = "Update a prescription",
        description = "Updates an existing prescription with the provided details."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prescription updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Prescription not found")
    })
    ResponseEntity<PrescriptionDto> update(
        @Parameter(description = "Details of the prescription to update", required = true) PrescriptionPostDto dto
    );
}

