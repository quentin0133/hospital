package fr.cfa.hospital.auth;

import fr.cfa.hospital.auth.dtos.LoginDto;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import fr.cfa.hospital.core.logs.LogController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @LogController
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginDto> authenticate(@RequestBody LoginPostDto login) {
        return ResponseEntity.ok(authService.authenticate(login));
    }
}
