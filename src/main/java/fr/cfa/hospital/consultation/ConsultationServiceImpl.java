package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.FileEmptyException;
import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.core.exception.ProvidedSaveIdException;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.tools.FileUtils;
import fr.cfa.hospital.file.File;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository repository;
    private final ConsultationMapper mapper;
    private final String uploadDir;

    public ConsultationServiceImpl(
            ConsultationRepository repository,
            ConsultationMapper mapper,
            @Value("${file.storage.path}") String uploadDir
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.uploadDir = uploadDir;
    }

    @Override
    public Page<ConsultationDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ConsultationDto save(ConsultationPostDto dto) {
        if (dto.getId() != null) {
            throw new ProvidedSaveIdException();
        }

        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public ConsultationDto update(ConsultationPostDto dto) {
        if (dto.getId() == null) {
            throw new NotProvidedUpdateIdException();
        }

        if (!repository.existsById(dto.getId())) {
            throw new ResourceNotFoundException("Consultation", dto.getId());
        }

        return mapper.toDto(repository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Consultation", id);
        repository.deleteById(id);
    }

    @Override
    public ConsultationDto findById(long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Consultation", id));
    }

    @Override
    public Page<ConsultationDto> findByPatientId(long patientId, Pageable pageable) {
        return repository.findByPatientId(patientId, pageable).map(mapper::toDto);
    }

    @Override
    public FileDto uploadFiles(FilePostDto dto) throws IOException {
        Consultation consultation = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", dto.getId()));

        MultipartFile multipartFile = dto.getFile();
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }

        if (consultation.getFile() != null) {
            FileUtils.delete(consultation.getFile(), uploadDir);
        }

        File newFile = FileUtils.upload(multipartFile, uploadDir);

        consultation.setFile(newFile);
        consultation = repository.saveAndFlush(consultation);

        return mapper.toDto(consultation).getFile();
    }
}
