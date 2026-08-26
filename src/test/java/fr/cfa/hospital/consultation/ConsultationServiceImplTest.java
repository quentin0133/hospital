package fr.cfa.hospital.consultation;

import fr.cfa.hospital.consultation.dtos.ConsultationDto;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.exception.FileEmptyException;
import fr.cfa.hospital.core.exception.NotProvidedUpdateIdException;
import fr.cfa.hospital.core.exception.ProvidedSaveIdException;
import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.core.tools.FileUtils;
import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.file.File;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.file.dtos.FilePostDto;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.patient.dtos.PatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ConsultationServiceImplTest {
    @TempDir
    Path tempDir;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    private ConsultationServiceImpl consultationServiceImpl;

    @BeforeEach
    void setUp() {
        consultationServiceImpl = new ConsultationServiceImpl(
            consultationRepository, consultationMapper, tempDir.toString()
        );
    }

    @Test
    void findByPatientId_validInput() {
        // Init of our mockup
        List<Consultation> consultations = new ArrayList<>();

        Consultation mockConsultation = new Consultation(
            1L, 1, LocalDate.of(2025, 5, 12),
            new Patient(), new Doctor(), List.of(), null
        );
        Consultation mockConsultation2 = new Consultation(
            2L, 1, LocalDate.of(2026, 8, 21),
            new Patient(), new Doctor(), List.of(), null
        );

        ConsultationDto expected1 = new ConsultationDto(
            1L, 0, LocalDate.of(2025, 5, 12),
            new PatientDto(),
            new DoctorDto(), List.of(), null
        );

        ConsultationDto expected2 = new ConsultationDto(
            2L, 0, LocalDate.of(2026, 8, 21),
            new PatientDto(),
            new DoctorDto(), List.of(), null
        );

        consultations.add(mockConsultation);
        consultations.add(mockConsultation2);

        Page<Consultation> consultationPage = new PageImpl<>(consultations);
        Pageable pageable = PageRequest.of(0, 10);

        // Mockup methods
        when(consultationRepository.findByPatientId(anyLong(), any(Pageable.class))).thenReturn(consultationPage);
        when(consultationMapper.toDto(any(Consultation.class))).thenAnswer(invocation -> toDto(invocation.getArgument(0)));

        // Method tested
        Page<ConsultationDto> result = consultationServiceImpl.findByPatientId(1L, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(result.getContent().get(0), expected1);
        assertEquals(result.getContent().get(1), expected2);

        // Check the number of calls
        verify(consultationRepository).findByPatientId(anyLong(), any(Pageable.class));
        verify(consultationMapper, times(2)).toDto(any(Consultation.class));
    }

    @Test
    void findByPatientId_ResultEmpty() {
        // Init of our mockup
        List<Consultation> consultations = new ArrayList<>();
        Page<Consultation> consultationPage = new PageImpl<>(consultations);
        Pageable pageable = PageRequest.of(0, 10);

        // Mockup methods
        when(consultationRepository.findByPatientId(anyLong(), any(Pageable.class))).thenReturn(consultationPage);

        // Method tested
        Page<ConsultationDto> result = consultationServiceImpl.findByPatientId(1L, pageable);

        assertEquals(0, result.getContent().size());

        // Check the number of calls
        verify(consultationRepository).findByPatientId(anyLong(), any(Pageable.class));
        verify(consultationMapper, never()).toDto(any(Consultation.class));
    }

    @Test
    void save() {
        // Init of our mockup
        ConsultationPostDto request = new ConsultationPostDto(
            null, 1, LocalDate.of(2025, 5, 12), 1, 1
        );

        Consultation consultation = new Consultation(
            1L, 1, LocalDate.of(2025, 5, 12), new Patient(), new Doctor(), List.of(), null
        );

        ConsultationDto consultationDto = new ConsultationDto(
            1L, 0, LocalDate.of(2025, 5, 12),
            new PatientDto(),
            new DoctorDto(), List.of(), null
        );

        // Mockup methods
        when(consultationRepository.saveAndFlush(any(Consultation.class))).thenReturn(consultation);
        when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDto);
        when(consultationMapper.toEntity(any(ConsultationPostDto.class))).thenReturn(consultation);

        ConsultationDto result = consultationServiceImpl.save(request);

        assertEquals(consultationDto, result);

        // Check the number of calls
        verify(consultationRepository).saveAndFlush(any(Consultation.class));
        verify(consultationMapper).toDto(any(Consultation.class));
        verify(consultationMapper).toEntity(any(ConsultationPostDto.class));
    }

    @Test
    void save_WithId() {
        // Init of our mockup
        ConsultationPostDto request = new ConsultationPostDto(
            1L, 1, LocalDate.of(2025, 5, 12), 1, 1
        );

        assertThrows(ProvidedSaveIdException.class, () -> consultationServiceImpl.save(request));

        // Check if there's no side effect
        verifyNoInteractions(consultationRepository, consultationMapper);
    }

    @Test
    void update() {
        // Init of our mockup
        ConsultationPostDto request = new ConsultationPostDto(
            1L, 0, LocalDate.of(2025, 5, 12), 1, 1
        );

        Consultation consultation = new Consultation(
            1L, 0, LocalDate.of(2025, 5, 12), new Patient(), new Doctor(), List.of(), null
        );

        ConsultationDto consultationDto = new ConsultationDto(
            1L, 0, LocalDate.of(2025, 5, 12),
            new PatientDto(),
            new DoctorDto(), List.of(), null
        );

        // Mockup methods
        when(consultationRepository.saveAndFlush(any(Consultation.class))).thenReturn(consultation);
        when(consultationRepository.existsById(anyLong())).thenReturn(true);
        when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDto);
        when(consultationMapper.toEntity(any(ConsultationPostDto.class))).thenReturn(consultation);

        ConsultationDto result = consultationServiceImpl.update(request);

        assertEquals(consultationDto, result);

        // Check the number of calls
        verify(consultationRepository).existsById(anyLong());
        verify(consultationRepository).saveAndFlush(any(Consultation.class));
        verify(consultationMapper).toDto(any(Consultation.class));
        verify(consultationMapper).toEntity(any(ConsultationPostDto.class));
    }

    @Test
    void update_idNotFound() {
        // Init of our mockup
        ConsultationPostDto request = new ConsultationPostDto(
            1L, 1, LocalDate.of(2025, 5, 12), 1, 1
        );

        // Mockup methods
        when(consultationRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> consultationServiceImpl.update(request));

        // Check the number of calls
        verify(consultationRepository).existsById(anyLong());
        verify(consultationRepository, never()).saveAndFlush(any(Consultation.class));

        verifyNoInteractions(consultationMapper);
    }

    @Test
    void update_idNull() {
        // Init of our mockup
        ConsultationPostDto request = new ConsultationPostDto(
            null, 1, LocalDate.of(2025, 5, 12), 1, 1
        );

        assertThrows(NotProvidedUpdateIdException.class, () -> consultationServiceImpl.update(request));

        // Check if there's no side effect
        verifyNoInteractions(consultationRepository, consultationMapper);
    }

    @Test
    void deleteById() {
        when(consultationRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(consultationRepository).deleteById(anyLong());

        consultationServiceImpl.deleteById(1L);

        verify(consultationRepository).deleteById(anyLong());
    }

    @Test
    void deleteById_IdNotFound() {
        when(consultationRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> consultationServiceImpl.deleteById(1L));

        verify(consultationRepository, never()).deleteById(anyLong());
    }

    @Test
    void uploadFile() throws IOException {
        try (MockedStatic<FileUtils> fileUtilsMocked = mockStatic(FileUtils.class)) {
            MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "Hello World".getBytes()
            );
            FilePostDto fileCommandDto = new FilePostDto(1, multipartFile);
            File file = new File("file.pdf", "file-sqdx.pdf");
            FileDto expected = new FileDto("file.pdf", "file-sqdx.pdf");

            Consultation consultation = new Consultation(
                1L, 1, LocalDate.of(2025, 5, 12), new Patient(), new Doctor(), List.of(), file
            );

            ConsultationDto consultationDto = new ConsultationDto(
                1L, 0, LocalDate.of(2025, 5, 12),
                new PatientDto(), new DoctorDto(), List.of(), expected
            );

            fileUtilsMocked.when(() -> FileUtils.delete(any(File.class), anyString()))
                .thenAnswer(invocation -> null);
            fileUtilsMocked.when(() -> FileUtils.upload(any(MultipartFile.class), anyString()))
                .thenReturn(file);

            when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(consultation));
            when(consultationRepository.saveAndFlush(any(Consultation.class))).thenReturn(consultation);
            when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDto);

            FileDto result = consultationServiceImpl.uploadFiles(fileCommandDto);

            assertEquals(result, expected);

            verify(consultationRepository).findById(anyLong());
            verify(consultationRepository).saveAndFlush(any(Consultation.class));
            verify(consultationMapper).toDto(any(Consultation.class));
            fileUtilsMocked.verify(() -> FileUtils.delete(any(File.class), anyString()));
            fileUtilsMocked.verify(() -> FileUtils.upload(any(MultipartFile.class), anyString()));
        }
    }

    @Test
    void uploadFile_empty() {
        try (MockedStatic<FileUtils> fileUtilsMocked = mockStatic(FileUtils.class)) {
            MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", new byte[0]
            );
            FilePostDto fileCommandDto = new FilePostDto(1, multipartFile);
            File file = new File("file.pdf", "file-sqdx.pdf");

            Consultation consultation = new Consultation(
                1L, 1, LocalDate.of(2025, 5, 12), new Patient(), new Doctor(), List.of(), file
            );

            when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(consultation));

            assertThrows(FileEmptyException.class, () -> consultationServiceImpl.uploadFiles(fileCommandDto));

            verify(consultationRepository).findById(anyLong());
            verify(consultationRepository, never()).saveAndFlush(any(Consultation.class));
            verifyNoInteractions(consultationMapper);
            fileUtilsMocked.verifyNoInteractions();
        }
    }

    @Test
    void uploadFile_null() {
        try (MockedStatic<FileUtils> fileUtilsMocked = mockStatic(FileUtils.class)) {
            FilePostDto fileCommandDto = new FilePostDto(1, null);
            File file = new File("file.pdf", "file-sqdx.pdf");

            Patient patient = new Patient(1L, 1, "Michel", new ArrayList<>());
            Doctor doctor = new Doctor(1L, 1, "Bernard", new ArrayList<>());

            Consultation consultation = new Consultation(
                1L, 1, LocalDate.of(2025, 5, 12), patient, doctor, List.of(), file
            );

            when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(consultation));

            fileUtilsMocked.when(() -> FileUtils.delete(any(File.class), anyString()))
                .thenAnswer(invocation -> null);

            assertThrows(FileEmptyException.class, () -> consultationServiceImpl.uploadFiles(fileCommandDto));

            verify(consultationRepository).findById(anyLong());
            verify(consultationRepository, never()).saveAndFlush(any(Consultation.class));
            verifyNoInteractions(consultationMapper);
            fileUtilsMocked.verifyNoInteractions();
        }
    }

    @Test
    void uploadFile_IOException() {
        try (MockedStatic<FileUtils> fileUtilsMocked = mockStatic(FileUtils.class)) {
            MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "Hello World".getBytes()
            );
            FilePostDto fileCommandDto = new FilePostDto(1, multipartFile);
            File file = new File("file.pdf", "file-sqdx.pdf");

            Consultation consultation = new Consultation(
                1L, 1, LocalDate.of(2025, 5, 12), new Patient(), new Doctor(), List.of(), file
            );

            fileUtilsMocked.when(() -> FileUtils.delete(any(File.class), anyString()))
                .thenThrow(new IOException());

            when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(consultation));

            assertThrows(IOException.class, () -> consultationServiceImpl.uploadFiles(fileCommandDto));

            verify(consultationRepository).findById(anyLong());
            verify(consultationRepository, never()).saveAndFlush(any(Consultation.class));
            fileUtilsMocked.verify(() -> FileUtils.delete(any(File.class), anyString()));
            fileUtilsMocked.verify(() -> FileUtils.upload(any(MultipartFile.class), anyString()), never());
            verifyNoInteractions(consultationMapper);
        }
    }

    private ConsultationDto toDto(Consultation entity) {
        long doctorId = entity.getDoctor().getId() != null ? entity.getDoctor().getId() : 0;
        long patientId = entity.getPatient().getId() != null ? entity.getPatient().getId() : 0;
        ConsultationDto dto = new ConsultationDto();

        dto.setId(entity.getId());
        dto.setFile(null);
        dto.setDoctor(new DoctorDto(doctorId, entity.getDoctor().getVersion(), entity.getDoctor().getName()));
        dto.setPatient(new PatientDto(patientId, entity.getPatient().getVersion(), entity.getPatient().getName()));
        dto.setMedications(new ArrayList<>());
        dto.setDate(entity.getDate());

        return dto;
    }
}