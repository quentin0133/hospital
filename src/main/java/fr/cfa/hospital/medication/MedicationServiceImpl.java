package fr.cfa.hospital.medication;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.medication.dtos.MedicationGetDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository repository;
    private final MedicationMapper mapper;

    @Override
    public List<MedicationGetDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toDto).toList();
    }

    @Override
    public MedicationGetDto findById(long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    @Override
    public MedicationGetDto save(MedicationPostDto dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public MedicationGetDto update(MedicationPostDto dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Tag", id);
        repository.deleteById(id);
    }
}
