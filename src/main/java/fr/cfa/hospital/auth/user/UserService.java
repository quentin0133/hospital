package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.user.dtos.ChangePasswordDto;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.auth.user.dtos.UserPostDto;
import fr.cfa.hospital.core.exception.PasswordException;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.patient.dtos.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Page<UserDto> findAll(Pageable pageable);

    UserDto save(UserPostDto newUser) throws PasswordException;

    UserDto findById(String idUser) throws ResourceNotFoundException;

    void checkPassword(String idUser, String encodedPassword) throws PasswordException;

    void updatePassword(String id, String encodedPassword);
}
