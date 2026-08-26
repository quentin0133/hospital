package fr.cfa.hospital.patient;

import fr.cfa.hospital.patient.dtos.PatientDto;
import fr.cfa.hospital.patient.dtos.PatientPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    Page<PatientDto> findAll(Pageable pageable);

    Page<PatientDto> findByName(String name, Pageable pageable);

    PatientDto findById(long id);

    PatientDto save(PatientPostDto dto);

    PatientDto update(PatientPostDto dto);

    void deleteById(long id);
}
