package fr.cfa.hospital.medication;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.logs.LogController;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MedicationControllerImpl implements MedicationController {
    private final MedicationService service;

    public MedicationControllerImpl(MedicationService service) {
        this.service = service;
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicationDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationDto> save(@RequestBody MedicationPostDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationDto> update(@RequestBody MedicationPostDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.update(dto));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws ResourceNotFoundException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
