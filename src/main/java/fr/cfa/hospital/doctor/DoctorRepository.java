package fr.cfa.hospital.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Page<Doctor> findByNameContains(String name, Pageable pageable);
}
