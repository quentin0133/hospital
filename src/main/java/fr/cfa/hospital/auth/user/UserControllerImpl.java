package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.user.account.management.AccountManagementService;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.auth.user.dtos.UserTemporaryCredentials;
import fr.cfa.hospital.core.logs.LogController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserControllerImpl implements UserController {
    private final UserService service;
    private final AccountManagementService accountService;

    public UserControllerImpl(UserService service, AccountManagementService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add-patient/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTemporaryCredentials> savePatient(@RequestParam String idPatient, @PathVariable String username) {
        return ResponseEntity.ok(accountService.savePatient(idPatient, username));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add-doctor/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTemporaryCredentials> saveDoctor(@RequestParam String idDoctor, @PathVariable String username) {
        return ResponseEntity.ok(accountService.saveDoctor(idDoctor, username));
    }
}
