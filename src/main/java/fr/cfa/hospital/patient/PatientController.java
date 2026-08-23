package fr.cfa.hospital.patient;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.patient.dtos.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService service;

    @GetMapping(
        value = {
            "",
            "/{name}"
        },
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"page", "size"}
    )
    public ResponseEntity<Page<PatientDto>> findAllByIdOrName(
        @PathVariable(required = false) Optional<String> name,
        Pageable pageable
    ) {
        return ResponseEntity.ok(name.isPresent() ?
            service.findByName(name.get(), pageable) :
            service.findAll(pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> save(@RequestBody PatientDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> update(@RequestBody PatientDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) throws ResourceNotFoundException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
