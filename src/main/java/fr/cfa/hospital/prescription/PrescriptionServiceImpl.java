package fr.cfa.hospital.prescription;

import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.core.utils.LogUtils;
import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public PrescriptionLightDto update(PrescriptionCommandDto prescription) {
        LogUtils.logEnter(log, prescription);

        if (prescription.getId() == null)
            throw new NotProvidedUpdateIdException();

        PrescriptionLightDto dto = prescriptionMapper.toDto(prescriptionRepository.saveAndFlush(prescriptionMapper.toEntity(prescription)));

        LogUtils.logExit(log, dto);

        return dto;
    }
}
