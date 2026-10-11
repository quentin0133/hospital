package fr.cfa.hospital.medication;

import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.core.exception.ProvidedSaveIdException;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository repository;
    private final MedicationMapper mapper;

    public MedicationServiceImpl(MedicationRepository repository, MedicationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<MedicationDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toDto).toList();
    }

    @Override
    public MedicationDto findById(String id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    @Override
    public MedicationDto save(MedicationPostDto dto) {
        if (dto.getId() != null) {
            throw new ProvidedSaveIdException();
        }

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public MedicationDto update(MedicationPostDto dto) {
        if (dto.getId() == null) {
            throw new NotProvidedUpdateIdException();
        }

        if (!repository.existsById(dto.getId())) {
            throw new ResourceNotFoundException("Medication", dto.getId());
        }

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Medication", id);
        repository.deleteById(id);
    }
}
