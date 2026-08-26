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
class PrescriptionPostDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        PrescriptionPostDto dto = new PrescriptionPostDto();

        assertNotNull(dto);
        assertNull(dto.getId());
        assertEquals(0, dto.getMedicationId());
        assertEquals(0, dto.getConsultationId());
        assertEquals(0, dto.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        PrescriptionPostDto dto = new PrescriptionPostDto(1L, 1, 1, 1, 1);

        assertEquals(1L, dto.getId());
        assertEquals(1, dto.getConsultationId());
        assertEquals(1, dto.getMedicationId());
        assertEquals(1, dto.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        PrescriptionPostDto dto = new PrescriptionPostDto();
        dto.setId(1L);
        dto.setConsultationId(1);
        dto.setMedicationId(1);
        dto.setQuantity(1);

        String json = objectMapper.writeValueAsString(dto);
        PrescriptionPostDto result = objectMapper.readValue(json, PrescriptionPostDto.class);

        assertEquals(dto, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        PrescriptionPostDto original = new PrescriptionPostDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new PrescriptionPostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new PrescriptionPostDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setId(1L);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setId(null);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentConsultationId() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setConsultationId(2);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setConsultationId(1);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentMedicationId() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setMedicationId(1);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setConsultationId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentVersion() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setVersion(1);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setVersion(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentQuantity() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setQuantity(1);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setQuantity(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setId(1L);
        p1.setVersion(1);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setId(1L);
        p2.setVersion(1);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PrescriptionPostDto p1 = new PrescriptionPostDto();
        p1.setId(1L);
        p1.setVersion(1);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionPostDto p2 = new PrescriptionPostDto();
        p2.setId(1L);
        p2.setVersion(1);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PrescriptionPostDto original = new PrescriptionPostDto();
        original.setId(1L);
        original.setVersion(1);
        original.setConsultationId(1);
        original.setMedicationId(1);
        original.setQuantity(1);

        String expected = "PrescriptionPostDto{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", medicationId=" + original.getVersion() +
                ", consultationId=" + original.getVersion() +
                ", quantity=" + original.getVersion() +
                '}';
        assertEquals(expected, original.toString());
    }
}