package fr.cfa.hospital.auth.user.account.management;

import fr.cfa.hospital.auth.user.dtos.ChangePasswordDto;
import fr.cfa.hospital.auth.user.dtos.UserTemporaryCredentials;

public interface AccountManagementService {
    UserTemporaryCredentials savePatient(String idPatient, String username);

    UserTemporaryCredentials saveDoctor(String idDoctor, String username);

    void changePassword(ChangePasswordDto changePasswordDto);
}
