package fr.cfa.hospital.prescription.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.medication.dtos.MedicationDto;
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
        assertNull(original.getMedication());
        assertEquals(0, original.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        MedicationDto medication = new MedicationDto();
        medication.setId("1");
        PrescriptionDto original = new PrescriptionDto(medication, 1);

        assertEquals(medication, original.getMedication());
        assertEquals(1, original.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        MedicationDto medication = new MedicationDto();
        medication.setId("1");
        PrescriptionDto original = new PrescriptionDto();
        original.setMedication(medication);
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
    void testEquals_shouldBeEqualsWhenDifferentMedicationId() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setMedication(new MedicationDto("1", 0, ""));

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setMedication(new MedicationDto("2", 0, ""));

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentQuantity() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setQuantity(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setQuantity(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setMedication(null);
        p1.setQuantity(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setMedication(null);
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PrescriptionDto p1 = new PrescriptionDto();
        p1.setMedication(null);
        p1.setQuantity(1);

        PrescriptionDto p2 = new PrescriptionDto();
        p2.setMedication(null);
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PrescriptionDto original = new PrescriptionDto();
        original.setMedication(new MedicationDto());
        original.setQuantity(1);

        String expected = "PrescriptionDto{" +
            "medication=" + original.getMedication() +
            ", quantity=" + original.getQuantity() +
            '}';
        assertEquals(expected, original.toString());
    }
}