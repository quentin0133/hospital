package fr.cfa.hospital.auth.user.account.management;

import fr.cfa.hospital.auth.user.dtos.ChangePasswordDto;
import fr.cfa.hospital.auth.user.dtos.UserTemporaryCredentials;

/**
 * Service contract for provisioning user accounts linked to domain profiles
 * and managing user credentials.
 */
public interface AccountManagementService {

    /**
     * Creates a new user account for a specified patient, associates it with the patient profile,
     * and returns the generated temporary credentials.
     *
     * @param idPatient the unique identifier of the patient
     * @param username  the username for the new account
     * @return the generated temporary credentials containing the username and raw password
     */
    UserTemporaryCredentials savePatient(String idPatient, String username);

    /**
     * Creates a new user account for a specified doctor, associates it with the doctor profile,
     * and returns the generated temporary credentials.
     *
     * @param idDoctor the unique identifier of the doctor
     * @param username the username for the new account
     * @return the generated temporary credentials containing the username and raw password
     */
    UserTemporaryCredentials saveDoctor(String idDoctor, String username);

    /**
     * Updates the password for the currently authenticated user after validating their existing credentials.
     *
     * @param changePasswordDto DTO containing the current password and the new password
     */
    void changePassword(ChangePasswordDto changePasswordDto);
}