package fr.cfa.hospital.doctor;

import fr.cfa.hospital.patient.dtos.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<PatientDto> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
