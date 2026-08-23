package fr.cfa.hospital.prescription.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PrescriptionCommandDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        PrescriptionCommandDto dto = new PrescriptionCommandDto();

        assertNotNull(dto);
        assertNull(dto.getId());
        assertEquals(0, dto.getMedicationId());
        assertEquals(0, dto.getConsultationId());
        assertEquals(0, dto.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        PrescriptionCommandDto dto = new PrescriptionCommandDto(1L, 1, 1, 1);

        assertEquals(1L, dto.getId());
        assertEquals(1, dto.getConsultationId());
        assertEquals(1, dto.getMedicationId());
        assertEquals(1, dto.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        PrescriptionCommandDto dto = new PrescriptionCommandDto();
        dto.setId(1L);
        dto.setConsultationId(1);
        dto.setMedicationId(1);
        dto.setQuantity(1);

        String json = objectMapper.writeValueAsString(dto);
        PrescriptionCommandDto result = objectMapper.readValue(json, PrescriptionCommandDto.class);

        assertEquals(dto, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        PrescriptionCommandDto original = new PrescriptionCommandDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new PrescriptionCommandDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new PrescriptionCommandDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        PrescriptionCommandDto p1 = new PrescriptionCommandDto();
        p1.setId(1L);

        PrescriptionCommandDto p2 = new PrescriptionCommandDto();
        p2.setId(null);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentConsultationId() {
        PrescriptionCommandDto p1 = new PrescriptionCommandDto();
        p1.setConsultationId(2);

        PrescriptionCommandDto p2 = new PrescriptionCommandDto();
        p2.setConsultationId(1);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentMedicationId() {
        PrescriptionCommandDto p1 = new PrescriptionCommandDto();
        p1.setMedicationId(1);

        PrescriptionCommandDto p2 = new PrescriptionCommandDto();
        p2.setConsultationId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentQuantity() {
        PrescriptionCommandDto p1 = new PrescriptionCommandDto();
        p1.setQuantity(1);

        PrescriptionCommandDto p2 = new PrescriptionCommandDto();
        p2.setQuantity(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PrescriptionCommandDto p1 = new PrescriptionCommandDto();
        p1.setId(1L);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionCommandDto p2 = new PrescriptionCommandDto();
        p2.setId(1L);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PrescriptionCommandDto p1 = new PrescriptionCommandDto();
        p1.setId(1L);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionCommandDto p2 = new PrescriptionCommandDto();
        p2.setId(1L);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PrescriptionCommandDto dto = new PrescriptionCommandDto();
        dto.setId(1L);
        dto.setConsultationId(1);
        dto.setMedicationId(1);
        dto.setQuantity(1);

        String expected = "PrescriptionPostUpdateDto{" +
            "id=" + dto.getId() +
            ", medicationId=" + dto.getMedicationId() +
            ", consultationId=" + dto.getConsultationId() +
            ", quantity=" + dto.getQuantity() +
            '}';
        assertEquals(expected, dto.toString());
    }
}