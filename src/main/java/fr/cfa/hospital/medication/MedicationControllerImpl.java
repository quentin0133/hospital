package fr.cfa.hospital.medication;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.logs.LogController;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medication")
public class MedicationControllerImpl implements MedicationController {
    private final MedicationService service;

    public MedicationControllerImpl(MedicationService service) {
        this.service = service;
    }

    @Override
    @LogController
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    @LogController
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationDto> save(@RequestBody MedicationPostDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @Override
    @LogController
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicationDto> update(@RequestBody MedicationPostDto dto) throws ResourceNotFoundException {
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
