package fr.cfa.hospital.patient;

import fr.cfa.hospital.auth.user.UserService;
import fr.cfa.hospital.core.exception.AccountExistsException;
import fr.cfa.hospital.core.exception.AccountNotFoundException;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.patient.dtos.PatientDto;
import fr.cfa.hospital.patient.dtos.PatientPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final PatientMapper mapper;
    private final UserService userService;

    public PatientServiceImpl(PatientRepository repository, PatientMapper mapper, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Page<PatientDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public Page<PatientDto> findByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toDto);
    }

    @Override
    public PatientDto findById(String id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    @Override
    public PatientDto save(PatientPostDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public PatientDto update(PatientPostDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Patient", id);
        repository.deleteById(id);
    }

    @Override
    public void linkUser(String idPatient, String idUser) throws AccountExistsException {
        Patient patient = repository.findById(idPatient)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", idPatient));

        if (patient.getIdUser() != null) {
            throw new AccountExistsException();
        }

        patient.setIdUser(idUser);
        repository.save(patient);
    }

    @Override
    public void unlinkUser(String idPatient) throws AccountNotFoundException {
        Patient patient = repository.findById(idPatient)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", idPatient));

        if (patient.getIdUser() == null) {
            throw new AccountNotFoundException();
        }

        patient.setIdUser(null);
        repository.save(patient);
    }
}
