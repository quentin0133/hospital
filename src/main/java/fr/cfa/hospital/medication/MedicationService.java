package fr.cfa.hospital.medication;

import fr.cfa.hospital.medication.dtos.MedicationGetDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MedicationService {
    List<MedicationGetDto> findAll(Pageable pageable);

    MedicationGetDto findById(long id);

    MedicationGetDto save(MedicationPostDto dto);

    MedicationGetDto update(MedicationPostDto dto);

    void deleteById(long id);
}
