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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationControllerImpl implements ConsultationController {
    private final ConsultationService service;

    public ConsultationControllerImpl(ConsultationService service) {
        this.service = service;
    }

    @LogController
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<ConsultationDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @LogController
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @LogController
    @GetMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<ConsultationDto>> findByPatientId(@PathVariable long id, Pageable pageable) {
        return ResponseEntity.ok(service.findByPatientId(id, pageable));
    }

    @LogController
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> save(@RequestBody ConsultationPostDto dto) {
        return ResponseEntity.status(201).body(service.save(dto));
    }

    @LogController
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultationDto> update(@RequestBody ConsultationPostDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.update(dto));
    }

    @LogController
    @PutMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDto> uploadFile(@ModelAttribute FilePostDto requestDto) throws IOException {
        return ResponseEntity.ok(service.uploadFiles(requestDto));
    }

    @LogController
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) throws ResourceNotFoundException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
