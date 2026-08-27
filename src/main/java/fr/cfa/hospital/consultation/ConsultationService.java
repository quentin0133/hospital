package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ConsultationService {
    Page<ConsultationDto> findAll(Pageable pageable);

    ConsultationDto save(ConsultationPostDto dto);

    ConsultationDto update(ConsultationPostDto dto);

    void deleteById(String id);

    ConsultationDto findById(String id);

    Page<ConsultationDto> findByPatientId(String patientId, Pageable pageable);

    FileDto uploadFiles(FilePostDto dto) throws IOException;

    ConsultationDto addPrescription(String idConsultation, String idMedication, int quantity);

    ConsultationDto removePrescription(String idConsultation, String idMedication);
}
