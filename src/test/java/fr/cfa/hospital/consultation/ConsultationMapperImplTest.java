package fr.cfa.hospital.consultation;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.doctor.DoctorMapper;
import fr.cfa.hospital.doctor.DoctorRepository;
import fr.cfa.hospital.doctor.dtos.DoctorLightDto;
import fr.cfa.hospital.file.File;
import fr.cfa.hospital.file.FileMapper;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.medication.MedicationMapper;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.patient.PatientMapper;
import fr.cfa.hospital.patient.PatientRepository;
import fr.cfa.hospital.patient.dtos.PatientLightDto;
import fr.cfa.hospital.prescription.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ConsultationMapperImplTest {
    private ConsultationMapperImpl consultationMapperImpl;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicationMapper medicationMapper;

    @Mock
    private FileMapper fileMapper;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private DoctorMapper doctorMapper;

    @BeforeEach
    void setUp() {
        consultationMapperImpl = new ConsultationMapperImpl(
            prescriptionRepository, consultationRepository, doctorRepository,
            patientRepository, medicationMapper, fileMapper, patientMapper, doctorMapper
        );
    }

    @Test
    void testToEntityWithId_validInput() {
        ConsultationCommandDto dto = new ConsultationCommandDto(
            1L, LocalDate.of(2025, 5, 21), 1, 1
        );
        Consultation expected = new Consultation(
            1, LocalDate.of(2025, 5, 21),
            new Patient(), new Doctor(), new ArrayList<>(), new File()
        );

        when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(prescriptionRepository.findByConsultationId(anyLong())).thenReturn(List.of());
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));

        Consultation result = consultationMapperImpl.toEntity(dto);

        assertEquals(expected, result);

        verify(consultationRepository).findById(anyLong());
        verify(prescriptionRepository).findByConsultationId(anyLong());
        verify(patientRepository).findById(anyLong());
        verify(doctorRepository).findById(anyLong());
    }

    @Test
    void testToEntityWithoutId_shouldReturnEntityWhenDtoNotNull() {
        ConsultationCommandDto dto = new ConsultationCommandDto(
            null, LocalDate.of(2025, 5, 21), 1, 1
        );
        Consultation expected = new Consultation(
            0, LocalDate.of(2025, 5, 21),
            new Patient(), new Doctor(), List.of(), null
        );

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(new Patient()));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));

        Consultation result = consultationMapperImpl.toEntity(dto);

        assertEquals(expected, result);

        verify(patientRepository).findById(anyLong());
        verify(doctorRepository).findById(anyLong());
        verifyNoInteractions(prescriptionRepository, consultationRepository);
    }

    @Test
    void testToEntity_shouldThrowResourceNotFoundExceptionWhenConsultationNotFound() {
        ConsultationCommandDto dto = new ConsultationCommandDto(
            1L, LocalDate.of(2025, 5, 21), 1, 1
        );

        when(consultationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> consultationMapperImpl.toEntity(dto));

        verify(consultationRepository).findById(anyLong());
        verifyNoInteractions(prescriptionRepository, doctorRepository, patientRepository);
    }

    @Test
    void testToEntity_shouldThrowResourceNotFoundExceptionDoctorNotFound() {
        ConsultationCommandDto dto = new ConsultationCommandDto(
            null, LocalDate.of(2025, 5, 21), 1, 1
        );

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> consultationMapperImpl.toEntity(dto));

        verify(doctorRepository).findById(anyLong());
        verifyNoInteractions(consultationRepository, prescriptionRepository, patientRepository);
    }

    @Test
    void testToEntity_shouldThrowResourceNotFoundExceptionWhenPatientNotFound() {
        ConsultationCommandDto dto = new ConsultationCommandDto(
            null, LocalDate.of(2025, 5, 21), 1, 1
        );

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(new Doctor()));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> consultationMapperImpl.toEntity(dto));

        verify(doctorRepository).findById(anyLong());
        verify(patientRepository).findById(anyLong());
        verifyNoInteractions(consultationRepository, prescriptionRepository);
    }

    @Test
    void testToEntity_shouldReturnNullWhenDtoNull() {
        ConsultationCommandDto dto = null;

        assertNull(consultationMapperImpl.toEntity(dto));

        verifyNoInteractions(consultationRepository, prescriptionRepository, doctorRepository, patientRepository);
    }

    @Test
    void testToDto_validInput() {
        Consultation entity = new Consultation(
            1, LocalDate.of(2025, 5, 21),
            new Patient(), new Doctor(), List.of(), new File()
        );

        ConsultationFullDto expected = new ConsultationFullDto(
            1, LocalDate.of(2025, 5, 21),
            new PatientLightDto(), new DoctorLightDto(), List.of(), new FileDto()
        );

        when(fileMapper.toDto(any(File.class))).thenReturn(new FileDto());
        when(doctorMapper.toDto(any(Doctor.class))).thenReturn(new DoctorLightDto());
        when(patientMapper.toDto(any(Patient.class))).thenReturn(new PatientLightDto());
        when(prescriptionRepository.findByConsultationId(anyLong())).thenReturn(List.of());

        ConsultationFullDto result = consultationMapperImpl.toDto(entity);

        assertEquals(expected, result);

        verify(fileMapper).toDto(any(File.class));
        verify(doctorMapper).toDto(any(Doctor.class));
        verify(patientMapper).toDto(any(Patient.class));
        verify(prescriptionRepository).findByConsultationId(anyLong());
        verifyNoInteractions(medicationMapper);
    }

    @Test
    void testToDto_shouldReturnNullWhenEntityNull() {
        Consultation entity = null;

        assertNull(consultationMapperImpl.toDto(entity));

        verifyNoInteractions(fileMapper, doctorMapper, patientMapper, prescriptionRepository, medicationMapper);
    }
}