package fr.cfa.hospital.doctor;

import fr.cfa.hospital.core.exception.ResourceNotFoundException;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    private DoctorServiceImpl doctorServiceImpl;

    @BeforeEach
    void setUp() {
        doctorServiceImpl = new DoctorServiceImpl(doctorRepository, doctorMapper);
    }
    
    @Test
    void findAll() {
        List<Doctor> doctors = new ArrayList<>();

        Doctor mockDoctor = new Doctor("1", 1, "Frédéric", List.of(), null);
        Doctor mockDoctor2 = new Doctor("2", 1, "Michel", List.of(), null);

        DoctorDto expected1 = new DoctorDto("1", 0, "Frédéric", null);
        DoctorDto expected2 = new DoctorDto("2", 0, "Michel", null);

        doctors.add(mockDoctor);
        doctors.add(mockDoctor2);

        Page<Doctor> doctorPage = new PageImpl<>(doctors);
        Pageable pageable = PageRequest.of(0, 10);

        when(doctorRepository.findAll(any(Pageable.class))).thenReturn(doctorPage);
        when(doctorMapper.toDto(any(Doctor.class))).thenAnswer(invocation -> toDto(invocation.getArgument(0)));

        Page<DoctorDto> result = doctorServiceImpl.findAll(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(result.getContent().get(0), expected1);
        assertEquals(result.getContent().get(1), expected2);

        verify(doctorRepository).findAll(any(Pageable.class));
        verify(doctorMapper, times(2)).toDto(any(Doctor.class));
    }

    @Test
    void findByNameContains() {
        List<Doctor> doctors = new ArrayList<>();

        Doctor mockDoctor = new Doctor("1", 1, "Frédéric", List.of(), null);
        Doctor mockDoctor2 = new Doctor("2", 1, "Michel", List.of(), null);

        DoctorDto expected1 = new DoctorDto("1", 0, "Frédéric", null);
        DoctorDto expected2 = new DoctorDto("2", 0, "Michel", null);

        doctors.add(mockDoctor);
        doctors.add(mockDoctor2);

        String nameSearched = "e";

        Page<Doctor> doctorPage = new PageImpl<>(doctors);
        Pageable pageable = PageRequest.of(0, 10);

        when(doctorRepository.findByNameContains(anyString(), any(Pageable.class))).thenReturn(doctorPage);
        when(doctorMapper.toDto(any(Doctor.class))).thenAnswer(invocation -> toDto(invocation.getArgument(0)));

        Page<DoctorDto> result = doctorServiceImpl.findByNameContains(nameSearched, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(result.getContent().get(0), expected1);
        assertEquals(result.getContent().get(1), expected2);

        verify(doctorRepository).findByNameContains(anyString(), any(Pageable.class));
        verify(doctorMapper, times(2)).toDto(any(Doctor.class));
    }

    @Test
    void findById() {
        Doctor mockDoctor = new Doctor("1", 1, "Frédéric", List.of(), null);
        DoctorDto expected = new DoctorDto("1", 0, "Frédéric", null);
        String id = "1";

        when(doctorRepository.findById(anyString())).thenReturn(Optional.of(mockDoctor));
        when(doctorMapper.toDto(any(Doctor.class))).thenAnswer(invocation -> toDto(invocation.getArgument(0)));

        DoctorDto result = doctorServiceImpl.findById(id);

        assertEquals(result, expected);

        verify(doctorRepository).findById(anyString());
        verify(doctorMapper).toDto(any(Doctor.class));
    }

    @Test
    void findById_notFound() {
        String id = "1";

        when(doctorRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorServiceImpl.findById(id));

        verify(doctorRepository).findById(anyString());
        verifyNoInteractions(doctorMapper);
    }

    private DoctorDto toDto(Doctor entity) {
        DoctorDto dto = new DoctorDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}