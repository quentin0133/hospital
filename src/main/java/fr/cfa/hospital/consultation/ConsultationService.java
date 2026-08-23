package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationGetDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultationService {
    List<ConsultationGetDto> findAll(Pageable pageable);

    List<ConsultationGetDto> findAll();

    ConsultationGetDto save(ConsultationPostDto dto);

    ConsultationGetDto update(ConsultationPostDto dto);

    void deleteById(long id);

    ConsultationGetDto findById(long id);
}
