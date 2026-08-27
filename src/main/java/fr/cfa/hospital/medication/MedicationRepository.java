package fr.cfa.hospital.medication;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicationRepository extends MongoRepository<Medication, String> {
}
