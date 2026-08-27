package fr.cfa.hospital.consultation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultationRepository extends MongoRepository<Consultation, String> {
    Page<Consultation> findByPatientId(String patientId, Pageable pageable);
}
