package fr.cfa.hospital.prescription;

import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.core.tools.LogUtils;
import fr.cfa.hospital.prescription.dtos.PrescriptionDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionPostDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final Logger logger;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, PrescriptionMapper prescriptionMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.logger = LoggerFactory.getLogger(PrescriptionServiceImpl.class);
    }

    @Override
    public PrescriptionDto update(PrescriptionPostDto prescription) {
        LogUtils.logEnter(logger, prescription);

        if (prescription.getId() == null)
            throw new NotProvidedUpdateIdException();

        PrescriptionDto dto = prescriptionMapper.toDto(prescriptionRepository.saveAndFlush(prescriptionMapper.toEntity(prescription)));

        LogUtils.logExit(logger, dto);

        return dto;
    }
}
