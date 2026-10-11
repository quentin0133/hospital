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
import fr.cfa.hospital.medication.Medication;
import fr.cfa.hospital.medication.MedicationRepository;
import fr.cfa.hospital.prescription.Prescription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository repository;
    private final ConsultationMapper mapper;
    private final MedicationRepository medicationRepository;
    private final String uploadDir;

    public ConsultationServiceImpl(
            ConsultationRepository repository,
            ConsultationMapper mapper,
            MedicationRepository medicationRepository,
            @Value("${file.storage.path}") String uploadDir
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.medicationRepository = medicationRepository;
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

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ConsultationDto update(ConsultationPostDto dto) {
        if (dto.getId() == null) {
            throw new NotProvidedUpdateIdException();
        }

        if (!repository.existsById(dto.getId())) {
            throw new ResourceNotFoundException("Consultation", dto.getId());
        }

        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Consultation", id);
        repository.deleteById(id);
    }

    @Override
    public ConsultationDto findById(String id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Consultation", id));
    }

    @Override
    public Page<ConsultationDto> findByPatientId(String patientId, Pageable pageable) {
        return repository.findByPatientId(patientId, pageable).map(mapper::toDto);
    }

    @Override
    public FileDto uploadFiles(FilePostDto dto) throws IOException {
        Consultation consultation = repository.findById(dto.getIdConsultation())
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", dto.getIdConsultation()));

        MultipartFile multipartFile = dto.getFile();
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new FileEmptyException();
        }

        if (consultation.getFile() != null) {
            FileUtils.delete(consultation.getFile(), uploadDir);
        }

        File newFile = FileUtils.upload(multipartFile, uploadDir);

        consultation.setFile(newFile);

        return mapper.toDto(repository.save(consultation)).getFile();
    }

    @Override
    public ConsultationDto addPrescription(String idConsultation, String idMedication, int quantity) {
        Consultation consultation = repository.findById(idConsultation)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", idConsultation));

        consultation.getPrescriptions().stream()
                .filter(pres -> pres.getMedication().getId().trim().equals(idMedication.trim()))
                .findFirst()
                .ifPresentOrElse(pres ->
                    pres.setQuantity(quantity + pres.getQuantity()),
                    () -> {
                        Medication medication = medicationRepository.findById(idMedication)
                                .orElseThrow(() -> new ResourceNotFoundException("Medication", idMedication));

                        Prescription newPrescription = new Prescription();
                        newPrescription.setMedication(medication);
                        newPrescription.setQuantity(quantity);

                        consultation.getPrescriptions().add(newPrescription);
                    }
                );

        return mapper.toDto(repository.save(consultation));
    }

    @Override
    public ConsultationDto removePrescription(String idConsultation, String idMedication) {
        Consultation consultation = repository.findById(idConsultation)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", idConsultation));

        boolean isRemoved = consultation.getPrescriptions()
                .removeIf(pres -> pres.getMedication().getId().equals(idMedication));

        if (!isRemoved) {
            throw new ResourceNotFoundException("Medication", idMedication);
        }

        return mapper.toDto(repository.save(consultation));
    }
}
