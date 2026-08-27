package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * The interface Consultation controller.
 */
@Tag(name = "Consultations", description = "API to manage medical consultations and document uploads")
public interface ConsultationController {

    @Operation(summary = "Get all consultations", description = "Retrieves a paginated list of all consultations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<ConsultationDto>> findAll(@Parameter(description = "Pagination parameters") Pageable pageable);

    @Operation(summary = "Get consultation by ID", description = "Retrieves a specific consultation by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved consultation"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    ResponseEntity<ConsultationDto> findById(
            @PathVariable @Parameter(description = "Consultation ID", required = true) String id
    );

    @Operation(summary = "Get consultations by patient", description = "Retrieves a paginated list of consultations for a specific patient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    ResponseEntity<Page<ConsultationDto>> findByPatientId(
            @PathVariable @Parameter(description = "Patient ID", required = true) String id,
            @Parameter(description = "Pagination parameters") Pageable pageable
    );

    @Operation(summary = "Create a consultation", description = "Creates a new consultation in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<ConsultationDto> save(
            @RequestBody @Parameter(description = "Consultation details to create", required = true) ConsultationPostDto dto
    );

    @Operation(summary = "Update a consultation", description = "Updates an existing consultation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    ResponseEntity<ConsultationDto> update(
            @RequestBody @Parameter(description = "Consultation details to update", required = true) ConsultationPostDto dto
    ) throws ResourceNotFoundException;

    @Operation(summary = "Upload a consultation document", description = "Uploads a file (e.g., medical report) and attaches it to a consultation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or input"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    ResponseEntity<FileDto> uploadFile(
            @ModelAttribute @Parameter(description = "File data and consultation ID", required = true) FilePostDto requestDto
    ) throws IOException;

    @Operation(summary = "Delete a consultation", description = "Deletes an existing consultation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consultation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Consultation not found")
    })
    ResponseEntity<Void> deleteById(
            @PathVariable @Parameter(description = "Consultation ID", required = true) String id
    ) throws ResourceNotFoundException;

    @Operation(
            summary = "Add a prescription",
            description = "Adds a medication with a specific quantity to a consultation's list of prescriptions."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prescription successfully added",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ConsultationDto.class))),
            @ApiResponse(responseCode = "404", description = "Consultation or Medication not found", content = @Content)
    })
    ResponseEntity<ConsultationDto> addPrescription(
            @Parameter(description = "The ID of the consultation", example = "6a8f059b903bb0625fcf3991")
            @PathVariable String idConsultation,

            @Parameter(description = "The ID of the medication to prescribe", example = "6a8f059b903bb0625fcf3992")
            @PathVariable String idMedication,

            @Parameter(description = "The prescribed quantity", example = "2")
            @RequestParam int quantity
    ) throws ResourceNotFoundException;


    @Operation(
            summary = "Remove a prescription",
            description = "Removes a specific medication from the prescriptions of an existing consultation."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prescription successfully removed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ConsultationDto.class))),
            @ApiResponse(responseCode = "404", description = "Consultation not found, or Medication not present in this consultation", content = @Content)
    })
    ResponseEntity<ConsultationDto> removePrescription(
            @Parameter(description = "The ID of the consultation", example = "6a8f059b903bb0625fcf3991")
            @PathVariable String idConsultation,

            @Parameter(description = "The ID of the medication to remove", example = "6a8f059b903bb0625fcf3992")
            @PathVariable String idMedication
    ) throws ResourceNotFoundException;
}
