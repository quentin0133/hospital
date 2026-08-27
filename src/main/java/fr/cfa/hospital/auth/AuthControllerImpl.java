package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.account.management.AccountManagementService;
import fr.cfa.hospital.auth.user.dtos.ChangePasswordDto;
import fr.cfa.hospital.core.logs.LogController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PutMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        accountService.changePassword(changePasswordDto);
        return ResponseEntity.noContent().build();
    }
}
