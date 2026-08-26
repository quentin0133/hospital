package fr.cfa.hospital.prescription;

import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.medication.Medication;
import fr.cfa.hospital.prescription.dtos.PrescriptionDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionPostDto;
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
        Prescription prescription = new Prescription(1L, 1, new Medication(), new Consultation(), 5);
        PrescriptionPostDto request = new PrescriptionPostDto(1L, 1, 1, 1, 5);
        PrescriptionDto expected = new PrescriptionDto(1L, 0, 1, 1, 5);

        when(prescriptionRepository.saveAndFlush(any(Prescription.class))).thenReturn(prescription);
        when(prescriptionMapper.toEntity(any(PrescriptionPostDto.class))).thenReturn(prescription);
        when(prescriptionMapper.toDto(any(Prescription.class))).thenReturn(expected);

        PrescriptionDto result = prescriptionServiceImpl.update(request);

        assertEquals(result, expected);

        verify(prescriptionRepository).saveAndFlush(any(Prescription.class));
        verify(prescriptionMapper).toEntity(any(PrescriptionPostDto.class));
        verify(prescriptionMapper).toDto(any(Prescription.class));
    }

    @Test
    void update_withoutId() {
        PrescriptionPostDto request = new PrescriptionPostDto(null, 1, 1, 1, 5);

        assertThrows(NotProvidedUpdateIdException.class, () -> prescriptionServiceImpl.update(request));

        verifyNoInteractions(prescriptionRepository, prescriptionMapper);
    }

    @Test
    void testToString() {
        Prescription original = new Prescription();
        original.setId(1L);
        original.setVersion(1);
        original.setMedication(new Medication());
        original.setConsultation(new Consultation());
        original.setQuantity(1);

        String expected = "Prescription{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", medication=" + original.getMedication() +
                ", consultation=" + original.getConsultation() +
                ", quantity=" + original.getQuantity() +
                '}';
        assertEquals(expected, original.toString());
    }
}