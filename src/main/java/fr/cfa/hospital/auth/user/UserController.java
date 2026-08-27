package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.auth.user.dtos.UserTemporaryCredentials;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface User controller.
 */
@Tag(name = "User", description = "API to manage users")
public interface UserController {

    /**
     * Retrieve a paginated list of all users.
     *
     * @param pageable the pagination information
     * @return a paginated list of users
     */
    @Operation(
            summary = "Get all users",
            description = "Retrieves a paginated list of all users registered in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users")
    })
    ResponseEntity<Page<UserDto>> findAll(
            @Parameter(description = "Pagination and sorting parameters") Pageable pageable
    );

    /**
     * Create a user account for an existing patient.
     *
     * @param idPatient the ID of the patient
     * @param username  the desired username for the new account
     * @return the temporary credentials generated for the user
     */
    @Operation(
            summary = "Create patient account",
            description = "Generates a user account with ROLE_PATIENT for an existing patient and returns a one-time temporary password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully created"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "409", description = "Patient already has an account")
    })
    ResponseEntity<UserTemporaryCredentials> savePatient(
            @Parameter(description = "The unique ID of the patient", required = true)
            @PathVariable String idPatient,

            @Parameter(description = "The desired username", required = true)
            @RequestParam String username
    );

    /**
     * Create a user account for an existing doctor.
     *
     * @param idDoctor the ID of the doctor
     * @param username the desired username for the new account
     * @return the temporary credentials generated for the user
     */
    @Operation(
            summary = "Create doctor account",
            description = "Generates a user account with ROLE_DOCTOR for an existing doctor and returns a one-time temporary password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account successfully created"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "409", description = "Doctor already has an account")
    })
    ResponseEntity<UserTemporaryCredentials> saveDoctor(
            @Parameter(description = "The unique ID of the doctor", required = true)
            @PathVariable String idDoctor,

            @Parameter(description = "The desired username", required = true)
            @RequestParam String username
    );
}