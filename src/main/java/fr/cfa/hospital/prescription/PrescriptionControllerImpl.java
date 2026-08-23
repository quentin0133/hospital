package fr.cfa.hospital.prescription;

import fr.cfa.hospital.core.logs.LogController;
import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
public class PrescriptionControllerImpl implements PrescriptionController {
    private final PrescriptionService prescriptionService;

    @Override
    @LogController
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrescriptionLightDto> update(@RequestBody PrescriptionCommandDto dto) {
        return ResponseEntity.ok(prescriptionService.update(dto));
    }
}
