package fr.cfa.hospital.consultation.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.patient.dtos.PatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ConsultationDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    }

    @Test
    void testConstructorEmpty_validInput() {
        ConsultationDto original = new ConsultationDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertEquals(0, original.getVersion());
        assertNull(original.getDate());
        assertNull(original.getPatient());
        assertNull(original.getDoctor());
        assertEquals(new ArrayList<>(), original.getMedications());
        assertNull(original.getFile());
    }

    @Test
    void testConstructorFull_validInput() {
        ConsultationDto original = new ConsultationDto(
            1L,
            1,
            LocalDate.now(),
            new PatientDto(),
            new DoctorDto(),
            new ArrayList<>(),
            new FileDto()
        );

        assertEquals(1, original.getId());
        assertEquals(1, original.getVersion());
        assertEquals(LocalDate.now(), original.getDate());
        assertEquals(new PatientDto(), original.getPatient());
        assertEquals(new DoctorDto(), original.getDoctor());
        assertEquals(new ArrayList<>(), original.getMedications());
        assertEquals(new FileDto(), original.getFile());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        ConsultationDto original = new ConsultationDto();
        original.setId(1);
        original.setDate(LocalDate.now());
        original.setDoctor(new DoctorDto());
        original.setPatient(new PatientDto());
        original.setMedications(new ArrayList<>());
        original.setFile(new FileDto());

        String json = objectMapper.writeValueAsString(original);
        ConsultationDto result = objectMapper.readValue(json, ConsultationDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new ConsultationDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new ConsultationDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setId(1L);

        ConsultationDto c2 = new ConsultationDto();
        c2.setId(2L);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentVersion() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setVersion(1);

        ConsultationDto c2 = new ConsultationDto();
        c2.setVersion(2);

        assertEquals(c1, c2);
    }


    @Test
    void testEquals_shouldBeEqualsWhenDifferentDate() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setDate(LocalDate.now());

        ConsultationDto c2 = new ConsultationDto();
        c2.setDate(null);

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentDoctor() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setDoctor(new DoctorDto());

        ConsultationDto c2 = new ConsultationDto();
        c2.setDoctor(null);

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentPatient() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setPatient(new PatientDto());

        ConsultationDto c2 = new ConsultationDto();
        c2.setPatient(null);

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentMedications() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setMedications(new ArrayList<>());

        ConsultationDto c2 = new ConsultationDto();
        c2.setMedications(null);

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentFile() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setFile(new FileDto());

        ConsultationDto c2 = new ConsultationDto();
        c2.setFile(null);

        assertEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setId(1);
        c1.setVersion(1);
        c1.setDate(LocalDate.now());
        c1.setDoctor(new DoctorDto());
        c1.setPatient(new PatientDto());
        c1.setMedications(new ArrayList<>());
        c1.setFile(new FileDto());

        ConsultationDto c2 = new ConsultationDto();
        c2.setId(1);
        c2.setVersion(1);
        c2.setDate(LocalDate.now());
        c2.setDoctor(new DoctorDto());
        c2.setPatient(new PatientDto());
        c2.setMedications(new ArrayList<>());
        c2.setFile(new FileDto());

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        ConsultationDto c1 = new ConsultationDto();
        c1.setId(1);
        c1.setVersion(1);
        c1.setDate(LocalDate.now());
        c1.setDoctor(new DoctorDto());
        c1.setPatient(new PatientDto());
        c1.setMedications(new ArrayList<>());
        c1.setFile(new FileDto());

        ConsultationDto c2 = new ConsultationDto();
        c2.setId(1);
        c2.setVersion(1);
        c2.setDate(LocalDate.now());
        c2.setDoctor(new DoctorDto());
        c2.setPatient(new PatientDto());
        c2.setMedications(new ArrayList<>());
        c2.setFile(new FileDto());

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        ConsultationDto original = new ConsultationDto();
        original.setId(1);
        original.setVersion(1);
        original.setDate(LocalDate.now());
        original.setDoctor(new DoctorDto());
        original.setPatient(new PatientDto());
        original.setMedications(new ArrayList<>());
        original.setFile(new FileDto());

        String expected = "ConsultationDto{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", doctor=" + original.getDoctor() +
                ", patient=" + original.getPatient() +
                ", medications=" + original.getMedications() +
                ", date=" + original.getDate() +
                ", file=" + original.getFile() +
                '}';
        assertEquals(expected, original.toString());
    }
}
