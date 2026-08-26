package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Auth controller.
 */
@Tag(name = "Authentication", description = "API to manage user authentication and JWT token generation")
public interface AuthController {

    /**
     * Authenticate user and generate token.
     *
     * @param login the login credentials
     * @return the response entity containing the user details and JWT token
     */
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user using their username and password, and returns a JWT token for accessing secured routes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    ResponseEntity<LoginDto> authenticate(
            @RequestBody @Parameter(description = "User credentials (username and password)", required = true) LoginPostDto login
    );
}