package fr.cfa.hospital.doctor;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.doctor.dtos.DoctorPostDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository repository;
    private final DoctorMapper mapper;

    public DoctorServiceImpl(DoctorRepository repository, DoctorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<DoctorDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public List<DoctorDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<DoctorDto> findByName(String search, Pageable pageable) {
        return repository.findByNameContains(search, pageable).map(mapper::toDto);
    }

    @Override
    public DoctorDto save(DoctorPostDto dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public DoctorDto update(DoctorPostDto dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Doctor", id);
        repository.deleteById(id);
    }

    @Override
    public DoctorDto findById(long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Doctor", id));
    }

    @Override
    public Page<DoctorDto> findByNameContains(String name, Pageable pageable) {
        return repository.findByNameContains(name, pageable).map(mapper::toDto);
    }
}
