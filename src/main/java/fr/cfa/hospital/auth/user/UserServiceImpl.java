package fr.cfa.hospital.auth.user;

import fr.cfa.hospital.auth.user.dtos.UserDto;
import fr.cfa.hospital.auth.user.dtos.UserPostDto;
import fr.cfa.hospital.core.exception.PasswordException;
import fr.cfa.hospital.core.exception.ProvidedSaveIdException;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).map(UserSecurity::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public UserDto save(UserPostDto newUser) throws ProvidedSaveIdException {
        if (newUser.getId() != null) {
            throw new ProvidedSaveIdException();
        }

        return mapper.toDto(repository.save(mapper.toEntity(newUser)));
    }

    @Override
    public UserDto findById(String idUser) throws ResourceNotFoundException {
        return mapper.toDto(repository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("User", idUser)));
    }

    @Override
    public void checkPassword(String idUser, String rawOldPassword) throws PasswordException {
        User user = repository.findById(idUser).orElseThrow(() -> new ResourceNotFoundException("User", idUser));
        if (!passwordEncoder.matches(rawOldPassword, user.getPassword())) {
            throw new PasswordException();
        }
    }

    @Override
    public void updatePassword(String idUser, String newEncodedPassword) {
        User user = repository.findById(idUser).orElseThrow();
        user.setPassword(newEncodedPassword);
        repository.save(user);
    }
}