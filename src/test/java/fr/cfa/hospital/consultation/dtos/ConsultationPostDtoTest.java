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
class ConsultationPostDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    }

    @Test
    void testConstructorEmpty_validInput() {
        ConsultationPostDto dto = new ConsultationPostDto();

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getDate());
        assertEquals(0, dto.getDoctorId());
        assertEquals(0, dto.getPatientId());
    }

    @Test
    void testConstructorFull_validInput() {
        ConsultationPostDto dto = new ConsultationPostDto(1L, 1, LocalDate.now(), 1, 1);

        assertEquals(1L, dto.getId());
        assertEquals(LocalDate.now(), dto.getDate());
        assertEquals(1, dto.getDoctorId());
        assertEquals(1, dto.getPatientId());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        ConsultationPostDto dto = new ConsultationPostDto();
        dto.setId(1L);
        dto.setDate(LocalDate.now());
        dto.setDoctorId(1);
        dto.setPatientId(1);

        String json = objectMapper.writeValueAsString(dto);
        ConsultationPostDto result = objectMapper.readValue(json, ConsultationPostDto.class);

        assertEquals(dto, result);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new ConsultationPostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new ConsultationPostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        ConsultationPostDto c1 = new ConsultationPostDto();
        c1.setId(1L);

        ConsultationPostDto c2 = new ConsultationPostDto();
        c2.setId(2L);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentDate() {
        ConsultationPostDto c1 = new ConsultationPostDto();
        c1.setDate(LocalDate.now());

        ConsultationPostDto c2 = new ConsultationPostDto();
        c2.setDate(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentDoctorId() {
        ConsultationPostDto c1 = new ConsultationPostDto();
        c1.setDoctorId(1);

        ConsultationPostDto c2 = new ConsultationPostDto();
        c2.setDoctorId(2);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentPatientId() {
        ConsultationPostDto c1 = new ConsultationPostDto();
        c1.setPatientId(1);

        ConsultationPostDto c2 = new ConsultationPostDto();
        c2.setPatientId(2);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        ConsultationPostDto c1 = new ConsultationPostDto();
        c1.setId(1L);
        c1.setDate(LocalDate.now());
        c1.setDoctorId(1);
        c1.setPatientId(1);

        ConsultationPostDto c2 = new ConsultationPostDto();
        c2.setId(1L);
        c2.setDate(LocalDate.now());
        c2.setDoctorId(1);
        c2.setPatientId(1);

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        ConsultationPostDto c1 = new ConsultationPostDto();
        c1.setId(1L);
        c1.setDate(LocalDate.now());
        c1.setDoctorId(1);
        c1.setPatientId(1);

        ConsultationPostDto c2 = new ConsultationPostDto();
        c2.setId(1L);
        c2.setDate(LocalDate.now());
        c2.setDoctorId(1);
        c2.setPatientId(1);

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        ConsultationPostDto dto = new ConsultationPostDto();
        dto.setId(1L);
        dto.setDate(LocalDate.now());
        dto.setDoctorId(1);
        dto.setPatientId(1);

        String expected = "ConsultationPostDto{" +
                "id=" + dto.getId() +
                ", version=" + dto.getVersion() +
                ", date=" + dto.getDate() +
                ", doctorId=" + dto.getDoctorId() +
                ", patientId=" + dto.getPatientId() +
                '}';
        assertEquals(expected, dto.toString());
    }
}