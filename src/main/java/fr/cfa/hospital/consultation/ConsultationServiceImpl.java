package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationGetDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository repository;
    private final ConsultationMapper mapper;

    @Override
    public List<ConsultationGetDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<ConsultationGetDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ConsultationGetDto save(ConsultationPostDto dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public ConsultationGetDto update(ConsultationPostDto dto) {
        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Tag", id);
        repository.deleteById(id);
    }

    @Override
    public ConsultationGetDto findById(long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Tag", id));
    }
}
