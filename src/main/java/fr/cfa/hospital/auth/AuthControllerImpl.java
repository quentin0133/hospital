package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import fr.cfa.hospital.auth.user.account.management.AccountManagementService;
import fr.cfa.hospital.auth.user.dtos.ChangePasswordDto;
import fr.cfa.hospital.core.logs.LogController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;
    private final AccountManagementService accountService;

    public AuthControllerImpl(AuthService authService, AccountManagementService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @Override
    @LogController
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginDto> authenticate(@RequestBody LoginPostDto login) {
        return ResponseEntity.ok(authService.authenticate(login));
    }

    @Override
    @LogController
    @PreAuthorize("#changePasswordDto.idUser == authentication.principal.id")
    @PutMapping(value = "/change-password/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        accountService.changePassword(changePasswordDto);
        return ResponseEntity.ok("The password has successfully been changed");
    }
}
