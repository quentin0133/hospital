package fr.cfa.hospital.patient;

import fr.cfa.hospital.patient.dtos.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    Page<PatientDto> findAll(Pageable pageable);

    Page<PatientDto> findByName(String name, Pageable pageable);

    PatientDto findById(long id);

    PatientDto save(PatientDto dto);

    PatientDto update(PatientDto dto);

    void deleteById(long id);
}
