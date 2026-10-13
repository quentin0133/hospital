package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.logs.LogController;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationControllerImpl implements ConsultationController {
    private final ConsultationService service;

    public ConsultationControllerImpl(ConsultationService service) {
        this.service = service;
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<ConsultationDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    @LogController
    @PreAuthorize("@securityUtils.canAccessPatient(#id) or hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<ConsultationDto>> findByPatientId(@PathVariable String id, Pageable pageable) {
        return ResponseEntity.ok(service.findByPatientId(id, pageable));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> save(@RequestBody ConsultationPostDto dto) {
        return ResponseEntity.status(201).body(service.save(dto));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> update(@RequestBody ConsultationPostDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.update(dto));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @PutMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> uploadFile(@ModelAttribute FilePostDto requestDto) throws IOException {
        return ResponseEntity.ok(service.uploadFiles(requestDto));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws ResourceNotFoundException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @PostMapping(value = "/{idConsultation}/medication/{idMedication}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> addPrescription(@PathVariable String idConsultation, @PathVariable String idMedication, @RequestParam int quantity) {
        return ResponseEntity.status(201).body(service.addPrescription(idConsultation, idMedication, quantity));
    }

    @Override
    @LogController
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @DeleteMapping(value = "/{idConsultation}/medication/{idMedication}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> removePrescription(@PathVariable String idConsultation, @PathVariable String idMedication) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.removePrescription(idConsultation, idMedication));
    }
}
