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
class PrescriptionLightDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        PrescriptionLightDto original = new PrescriptionLightDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertEquals(0, original.getMedicationId());
        assertEquals(0, original.getConsultationId());
        assertEquals(0, original.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        PrescriptionLightDto original = new PrescriptionLightDto(1, 1, 1, 1);

        assertEquals(1, original.getId());
        assertEquals(1, original.getConsultationId());
        assertEquals(1, original.getMedicationId());
        assertEquals(1, original.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        PrescriptionLightDto original = new PrescriptionLightDto();
        original.setId(1);
        original.setConsultationId(1);
        original.setMedicationId(1);
        original.setQuantity(1);

        String json = objectMapper.writeValueAsString(original);
        PrescriptionLightDto result = objectMapper.readValue(json, PrescriptionLightDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        PrescriptionLightDto original = new PrescriptionLightDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new PrescriptionLightDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new PrescriptionLightDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        PrescriptionLightDto p1 = new PrescriptionLightDto();
        p1.setId(1);

        PrescriptionLightDto p2 = new PrescriptionLightDto();
        p2.setId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentConsultationId() {
        PrescriptionLightDto p1 = new PrescriptionLightDto();
        p1.setConsultationId(1);

        PrescriptionLightDto p2 = new PrescriptionLightDto();
        p2.setConsultationId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentMedicationId() {
        PrescriptionLightDto p1 = new PrescriptionLightDto();
        p1.setMedicationId(1);

        PrescriptionLightDto p2 = new PrescriptionLightDto();
        p2.setMedicationId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentQuantity() {
        PrescriptionLightDto p1 = new PrescriptionLightDto();
        p1.setQuantity(1);

        PrescriptionLightDto p2 = new PrescriptionLightDto();
        p2.setQuantity(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PrescriptionLightDto p1 = new PrescriptionLightDto();
        p1.setId(1);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionLightDto p2 = new PrescriptionLightDto();
        p2.setId(1);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PrescriptionLightDto p1 = new PrescriptionLightDto();
        p1.setId(1);
        p1.setConsultationId(1);
        p1.setMedicationId(1);
        p1.setQuantity(1);

        PrescriptionLightDto p2 = new PrescriptionLightDto();
        p2.setId(1);
        p2.setConsultationId(1);
        p2.setMedicationId(1);
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PrescriptionLightDto original = new PrescriptionLightDto();
        original.setId(1);
        original.setConsultationId(1);
        original.setMedicationId(1);
        original.setQuantity(1);

        String expected = "PrescriptionLightDto{" +
            "id=" + original.getId() +
            ", medicationId=" + original.getMedicationId() +
            ", consultationId=" + original.getConsultationId() +
            ", quantity=" + original.getQuantity() +
            '}';
        assertEquals(expected, original.toString());
    }
}