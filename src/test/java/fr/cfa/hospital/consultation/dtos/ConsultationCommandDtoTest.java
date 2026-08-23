package fr.cfa.hospital.consultation.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ConsultationCommandDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    }

    @Test
    void testConstructorEmpty_validInput() {
        ConsultationCommandDto dto = new ConsultationCommandDto();

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getDate());
        assertEquals(0, dto.getDoctorId());
        assertEquals(0, dto.getPatientId());
    }

    @Test
    void testConstructorFull_validInput() {
        ConsultationCommandDto dto = new ConsultationCommandDto(1L, LocalDate.now(), 1, 1);

        assertEquals(1L, dto.getId());
        assertEquals(LocalDate.now(), dto.getDate());
        assertEquals(1, dto.getDoctorId());
        assertEquals(1, dto.getPatientId());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        ConsultationCommandDto dto = new ConsultationCommandDto();
        dto.setId(1L);
        dto.setDate(LocalDate.now());
        dto.setDoctorId(1);
        dto.setPatientId(1);

        String json = objectMapper.writeValueAsString(dto);
        ConsultationCommandDto result = objectMapper.readValue(json, ConsultationCommandDto.class);

        assertEquals(dto, result);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new ConsultationCommandDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new ConsultationCommandDto());
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
    void testEquals_shouldBeEqualsWhenDifferentDoctorId() {
        ConsultationCommandDto c1 = new ConsultationCommandDto();
        c1.setDoctorId(1);

        ConsultationCommandDto c2 = new ConsultationCommandDto();
        c2.setDoctorId(2);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentPatientId() {
        ConsultationCommandDto c1 = new ConsultationCommandDto();
        c1.setPatientId(1);

        ConsultationCommandDto c2 = new ConsultationCommandDto();
        c2.setPatientId(2);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        ConsultationCommandDto c1 = new ConsultationCommandDto();
        c1.setId(1L);
        c1.setDate(LocalDate.now());
        c1.setDoctorId(1);
        c1.setPatientId(1);

        ConsultationCommandDto c2 = new ConsultationCommandDto();
        c2.setId(1L);
        c2.setDate(LocalDate.now());
        c2.setDoctorId(1);
        c2.setPatientId(1);

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        ConsultationCommandDto c1 = new ConsultationCommandDto();
        c1.setId(1L);
        c1.setDate(LocalDate.now());
        c1.setDoctorId(1);
        c1.setPatientId(1);

        ConsultationCommandDto c2 = new ConsultationCommandDto();
        c2.setId(1L);
        c2.setDate(LocalDate.now());
        c2.setDoctorId(1);
        c2.setPatientId(1);

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        ConsultationCommandDto dto = new ConsultationCommandDto();
        dto.setId(1L);
        dto.setDate(LocalDate.now());
        dto.setDoctorId(1);
        dto.setPatientId(1);

        String expected = "ConsultationCommandDto{" +
            "id=" + dto.getId() +
            ", date=" + dto.getDate() +
            ", patientId=" + dto.getPatientId() +
            ", doctorId=" + dto.getDoctorId() +
            '}';
        assertEquals(expected, dto.toString());
    }
}