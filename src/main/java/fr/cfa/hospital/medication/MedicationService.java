package fr.cfa.hospital.medication;

import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicationService {
    List<MedicationDto> findAll(Pageable pageable);

    MedicationDto findById(long id);

    MedicationDto save(MedicationPostDto dto);

    MedicationDto update(MedicationPostDto dto);

    void deleteById(long id);
}
