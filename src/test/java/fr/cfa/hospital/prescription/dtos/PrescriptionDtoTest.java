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
class PrescriptionDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        PrescriptionDto original = new PrescriptionDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertEquals(0, original.getVersion());
        assertEquals(0, original.getMedicationId());
        assertEquals(0, original.getConsultationId());
        assertEquals(0, original.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        PrescriptionDto original = new PrescriptionDto(1L, 0, 1, 1, 1);

        assertEquals(1, original.getId());
        assertEquals(0, original.getVersion());
        assertEquals(1, original.getConsultationId());
        assertEquals(1, original.getMedicationId());
        assertEquals(1, original.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        PrescriptionDto original = new PrescriptionDto();
        original.setId(1);
        original.setVersion(0);
        original.setConsultationId(1);
        original.setMedicationId(1);
        original.setQuantity(1);

        String json = objectMapper.writeValueAsString(original);
        PrescriptionDto result = objectMapper.readValue(json, PrescriptionDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        PrescriptionDto original = new PrescriptionDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new PrescriptionDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new PrescriptionDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setId(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentConsultationId() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setConsultationId(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setConsultationId(2);

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentMedicationId() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setMedicationId(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setMedicationId(2);

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentQuantity() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setQuantity(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setQuantity(2);

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setId(1);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setId(1);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setId(1);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setId(1);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PrescriptionDto original = new PrescriptionDto();
        original.setId(1);
        original.setVersion(1);
        original.setConsultationId(1);
        original.setMedicationId(1);
        original.setQuantity(1);

        String expected = "PrescriptionDto{" +
            "id=" + original.getId() +
            ", version=" + original.getVersion() +
            ", medicationId=" + original.getMedicationId() +
            ", consultationId=" + original.getConsultationId() +
            ", quantity=" + original.getQuantity() +
            '}';
        assertEquals(expected, original.toString());
    }
}