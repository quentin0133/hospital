package fr.cfa.hospital.core.tools;

import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.doctor.DoctorRepository;
import fr.cfa.hospital.patient.PatientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityUtils")
public class SecurityUtils {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public SecurityUtils(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Check whether the target patient is associated with the logged-in user account.
     */
    public boolean canAccessPatient(String patientId) {
        String currentUserId = getCurrentUserId();
        if (currentUserId == null || patientId == null) {
            return false;
        }

        return patientRepository.findById(patientId)
            .map(patient -> currentUserId.equals(patient.getIdUser()))
            .orElse(false);
    }

    /**
     * Check whether the selected doctor is associated with the logged-in user account.
     */
    public boolean canAccessDoctor(String doctorId) {
        String currentUserId = getCurrentUserId();
        if (currentUserId == null || doctorId == null) {
            return false;
        }

        return doctorRepository.findById(doctorId)
            .map(doctor -> currentUserId.equals(doctor.getIdUser()))
            .orElse(false);
    }

    /**
     * Retrieves the ID of the logged-in user from the security context.
     */
    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof UserSecurity userSecurity) {
            return userSecurity.getUser().getId();
        }

        return null;
    }
}