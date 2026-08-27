package fr.cfa.hospital.auth.user.account.management;

import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.auth.user.UserService;
import fr.cfa.hospital.auth.user.dtos.ChangePasswordDto;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.auth.user.dtos.UserPostDto;
import fr.cfa.hospital.auth.user.dtos.UserTemporaryCredentials;
import fr.cfa.hospital.core.exception.AccountExistsException;
import fr.cfa.hospital.doctor.DoctorService;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.patient.PatientService;
import fr.cfa.hospital.patient.dtos.PatientDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {
    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;

    public AccountManagementServiceImpl(UserService userService, PatientService patientService, DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserTemporaryCredentials savePatient(String idPatient, String username) {
        PatientDto patient = patientService.findById(idPatient);

        if (patient.getIdUser() != null) {
            throw new AccountExistsException();
        }

        String rawPassword = generateRandomPassword();

        UserPostDto newUser = new UserPostDto();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRole("ROLE_PATIENT");
        newUser.setMustChangePassword(true);
        UserDto savedUser = userService.save(newUser);

        patientService.linkUser(idPatient, savedUser.getId());

        return new UserTemporaryCredentials(username, rawPassword);
    }

    @Override
    public UserTemporaryCredentials saveDoctor(String idDoctor, String username) {
        DoctorDto doctor = doctorService.findById(idDoctor);

        if (doctor.getIdUser() != null) {
            throw new AccountExistsException();
        }

        String rawPassword = generateRandomPassword();

        UserPostDto newUser = new UserPostDto();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));
        newUser.setRole("ROLE_DOCTOR");
        newUser.setMustChangePassword(true);
        UserDto savedUser = userService.save(newUser);

        doctorService.linkUser(idDoctor, savedUser.getId());

        return new UserTemporaryCredentials(username, rawPassword);
    }

    @Override
    public void changePassword(ChangePasswordDto changePassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        if (!(auth.getPrincipal() instanceof UserSecurity userSecurity)) {
            throw new BadCredentialsException("The principal is not an instance of UserSecurity");
        }

        userService.checkPassword(userSecurity.getUser().getId(), changePassword.getCurrentPassword());
        userService.updatePassword(userSecurity.getUser().getId(), passwordEncoder.encode(changePassword.getNewPassword()));
    }

    private String generateRandomPassword() {
        return "temp-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}
