package fr.cfa.hospital.consultation.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.cfa.hospital.doctor.dtos.DoctorLightDto;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.patient.dtos.PatientLightDto;
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
class ConsultationFullDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    }

    @Test
    void testConstructorEmpty_validInput() {
        ConsultationFullDto original = new ConsultationFullDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getDate());
        assertNull(original.getPatient());
        assertNull(original.getDoctor());
        assertEquals(new ArrayList<>(), original.getMedications());
        assertNull(original.getFile());
    }

    @Test
    void testConstructorFull_validInput() {
        ConsultationFullDto original = new ConsultationFullDto(
            1,
            LocalDate.now(),
            new PatientLightDto(),
            new DoctorLightDto(),
            new ArrayList<>(),
            new FileDto()
        );

        assertEquals(1, original.getId());
        assertEquals(LocalDate.now(), original.getDate());
        assertEquals(new PatientLightDto(), original.getPatient());
        assertEquals(new DoctorLightDto(), original.getDoctor());
        assertEquals(new ArrayList<>(), original.getMedications());
        assertEquals(new FileDto(), original.getFile());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        ConsultationFullDto original = new ConsultationFullDto();
        original.setId(1);
        original.setDate(LocalDate.now());
        original.setDoctor(new DoctorLightDto());
        original.setPatient(new PatientLightDto());
        original.setMedications(new ArrayList<>());
        original.setFile(new FileDto());

        String json = objectMapper.writeValueAsString(original);
        ConsultationFullDto result = objectMapper.readValue(json, ConsultationFullDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new ConsultationFullDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new ConsultationFullDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        ConsultationCommandDto c1 = new ConsultationCommandDto();
        c1.setId(1L);

        ConsultationCommandDto c2 = new ConsultationCommandDto();
        c2.setId(2L);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentDate() {
        ConsultationCommandDto c1 = new ConsultationCommandDto();
        c1.setDate(LocalDate.now());

        ConsultationCommandDto c2 = new ConsultationCommandDto();
        c2.setDate(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentDoctor() {
        ConsultationFullDto c1 = new ConsultationFullDto();
        c1.setDoctor(new DoctorLightDto());

        ConsultationFullDto c2 = new ConsultationFullDto();
        c2.setDoctor(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentPatient() {
        ConsultationFullDto c1 = new ConsultationFullDto();
        c1.setPatient(new PatientLightDto());

        ConsultationFullDto c2 = new ConsultationFullDto();
        c2.setPatient(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentMedications() {
        ConsultationFullDto c1 = new ConsultationFullDto();
        c1.setMedications(new ArrayList<>());

        ConsultationFullDto c2 = new ConsultationFullDto();
        c2.setMedications(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentFile() {
        ConsultationFullDto c1 = new ConsultationFullDto();
        c1.setFile(new FileDto());

        ConsultationFullDto c2 = new ConsultationFullDto();
        c2.setFile(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        ConsultationFullDto c1 = new ConsultationFullDto();
        c1.setId(1);
        c1.setDate(LocalDate.now());
        c1.setDoctor(new DoctorLightDto());
        c1.setPatient(new PatientLightDto());
        c1.setMedications(new ArrayList<>());
        c1.setFile(new FileDto());

        ConsultationFullDto c2 = new ConsultationFullDto();
        c2.setId(1);
        c2.setDate(LocalDate.now());
        c2.setDoctor(new DoctorLightDto());
        c2.setPatient(new PatientLightDto());
        c2.setMedications(new ArrayList<>());
        c2.setFile(new FileDto());

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        ConsultationFullDto c1 = new ConsultationFullDto();
        c1.setId(1);
        c1.setDate(LocalDate.now());
        c1.setDoctor(new DoctorLightDto());
        c1.setPatient(new PatientLightDto());
        c1.setMedications(new ArrayList<>());
        c1.setFile(new FileDto());

        ConsultationFullDto c2 = new ConsultationFullDto();
        c2.setId(1);
        c2.setDate(LocalDate.now());
        c2.setDoctor(new DoctorLightDto());
        c2.setPatient(new PatientLightDto());
        c2.setMedications(new ArrayList<>());
        c2.setFile(new FileDto());

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        ConsultationFullDto original = new ConsultationFullDto();
        original.setId(1);
        original.setDate(LocalDate.now());
        original.setDoctor(new DoctorLightDto());
        original.setPatient(new PatientLightDto());
        original.setMedications(new ArrayList<>());
        original.setFile(new FileDto());

        String expected = "ConsultationFullDto{" +
            "id=" + original.getId() +
            ", date=" + original.getDate() +
            ", patient=" + original.getPatient() +
            ", doctor=" + original.getDoctor() +
            ", medications=" + original.getMedications() +
            ", file=" + original.getFile() +
            '}';
        assertEquals(expected, original.toString());
    }
}
