package fr.cfa.hospital.consultation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    Page<Consultation> findByPatientId(long patientId, Pageable pageable);
}
