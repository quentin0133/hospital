package fr.cfa.hospital.prescription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Prescription repository.
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    /**
     * Find by consultation id list.
     *
     * @param consultationNumber the consultation number
     * @return the list
     */
    List<Prescription> findByConsultationId(long consultationNumber);
}
