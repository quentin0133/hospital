package fr.cfa.hospital.prescription;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.medication.Medication;
import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PrescriptionServiceImplTest {
    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionMapper prescriptionMapper;

    private PrescriptionServiceImpl prescriptionServiceImpl;

    @BeforeEach
    void setUp() {
        prescriptionServiceImpl = new PrescriptionServiceImpl(prescriptionRepository, prescriptionMapper);
    }

    @Test
    void update() {
        Prescription prescription = new Prescription(1, new Medication(), new Consultation(), 5);
        PrescriptionCommandDto request = new PrescriptionCommandDto(1L, 1, 1, 5);
        PrescriptionLightDto expected = new PrescriptionLightDto(1L, 1, 1, 5);

        when(prescriptionRepository.saveAndFlush(any(Prescription.class))).thenReturn(prescription);
        when(prescriptionMapper.toEntity(any(PrescriptionCommandDto.class))).thenReturn(prescription);
        when(prescriptionMapper.toDto(any(Prescription.class))).thenReturn(expected);

        PrescriptionLightDto result = prescriptionServiceImpl.update(request);

        assertEquals(result, expected);

        verify(prescriptionRepository).saveAndFlush(any(Prescription.class));
        verify(prescriptionMapper).toEntity(any(PrescriptionCommandDto.class));
        verify(prescriptionMapper).toDto(any(Prescription.class));
    }

    @Test
    void update_withoutId() {
        PrescriptionCommandDto request = new PrescriptionCommandDto(null, 1, 1, 5);

        assertThrows(NotProvidedUpdateIdException.class, () -> prescriptionServiceImpl.update(request));

        verifyNoInteractions(prescriptionRepository, prescriptionMapper);
    }

    @Test
    void testToString() {
        Prescription prescription = new Prescription(1, new Medication(), new Consultation(), 5);

        String expected = "Prescription{" +
            "id=" + prescription.getId() +
            ", quantity=" + prescription.getQuantity() +
            '}';
        assertEquals(expected, prescription.toString());
    }
}