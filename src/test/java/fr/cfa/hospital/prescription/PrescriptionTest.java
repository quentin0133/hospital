package fr.cfa.hospital.prescription;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.consultation.Consultation;
import fr.cfa.hospital.medication.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PrescriptionTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        Prescription original = new Prescription();

        assertNotNull(original);
        assertNull(original.getId());
        assertNull(original.getMedication());
        assertNull(original.getConsultation());
        assertEquals(0, original.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        Medication expectedMedication = new Medication();
        Consultation expectedConsultation = new Consultation();
        Prescription original = new Prescription(1L, 1, expectedMedication, expectedConsultation, 1);

        assertNotNull(original);
        assertEquals(1, original.getId());
        assertEquals(expectedMedication, original.getMedication());
        assertEquals(expectedConsultation, original.getConsultation());
        assertEquals(1, original.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        Prescription original = new Prescription();
        original.setId(1L);
        original.setMedication(new Medication());
        original.setConsultation(new Consultation());
        original.setQuantity(1);

        String json = objectMapper.writeValueAsString(original);
        Prescription result = objectMapper.readValue(json, Prescription.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        Prescription original = new Prescription();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new Prescription());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new Prescription());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        Prescription p1 = new Prescription();
        p1.setId(1L);

        Prescription p2 = new Prescription();
        p2.setId(2L);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentMedication() {
        Prescription p1 = new Prescription();
        p1.setMedication(new Medication());

        Prescription p2 = new Prescription();
        p2.setMedication(null);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentConsultation() {
        Prescription p1 = new Prescription();
        p1.setConsultation(new Consultation());

        Prescription p2 = new Prescription();
        p2.setConsultation(null);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentQuantity() {
        Prescription p1 = new Prescription();
        p1.setQuantity(1);

        Prescription p2 = new Prescription();
        p2.setQuantity(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        Prescription p1 = new Prescription();
        p1.setId(1L);
        p1.setMedication(new Medication());
        p1.setConsultation(new Consultation());
        p1.setQuantity(1);

        Prescription p2 = new Prescription();
        p2.setId(1L);
        p2.setMedication(new Medication());
        p2.setConsultation(new Consultation());
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Prescription p1 = new Prescription();
        p1.setId(1L);
        p1.setMedication(new Medication());
        p1.setConsultation(new Consultation());
        p1.setQuantity(1);

        Prescription p2 = new Prescription();
        p2.setId(1L);
        p2.setMedication(new Medication());
        p2.setConsultation(new Consultation());
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Prescription original = new Prescription();
        original.setId(1L);
        original.setMedication(new Medication());
        original.setConsultation(new Consultation());
        original.setQuantity(1);

        String expected = "Prescription{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", medication=" + original.getMedication() +
                ", consultation=" + original.getConsultation() +
                ", quantity=" + original.getQuantity() +
                '}';
        assertEquals(expected, original.toString());
    }
}