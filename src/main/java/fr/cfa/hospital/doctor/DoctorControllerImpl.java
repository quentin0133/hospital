package fr.cfa.hospital.doctor;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.logs.LogController;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.doctor.dtos.DoctorPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorControllerImpl implements DoctorController {
    private final DoctorService service;

    public DoctorControllerImpl(DoctorService service) {
        this.service = service;
    }

    @Override
    @LogController
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<DoctorDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Override
    @LogController
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    @LogController
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<Page<DoctorDto>> findByName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(service.findByName(name, pageable));
    }

    @Override
    @LogController
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDto> save(@RequestBody DoctorPostDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @Override
    @LogController
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDto> update(@RequestBody DoctorPostDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.update(dto));
    }

    @Override
    @LogController
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) throws ResourceNotFoundException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
