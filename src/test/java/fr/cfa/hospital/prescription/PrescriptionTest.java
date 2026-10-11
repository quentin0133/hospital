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
        assertNull(original.getMedication());
        assertEquals(0, original.getQuantity());
    }

    @Test
    void testConstructorFull_validInput() {
        Medication expectedMedication = new Medication();
        Consultation expectedConsultation = new Consultation();
        Prescription original = new Prescription(expectedMedication, 1);

        assertNotNull(original);
        assertEquals(expectedMedication, original.getMedication());
        assertEquals(1, original.getQuantity());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        Medication expectedMedication = new Medication();
        expectedMedication.setId("1");

        Prescription original = new Prescription();
        original.setMedication(expectedMedication);
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
    void testEquals_differentMedication() {
        Prescription p1 = new Prescription();
        p1.setMedication(new Medication());

        Prescription p2 = new Prescription();
        p2.setMedication(null);

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
        Medication expectedMedication = new Medication();
        expectedMedication.setId("1");
        Prescription p1 = new Prescription();
        p1.setMedication(expectedMedication);
        p1.setQuantity(1);

        Prescription p2 = new Prescription();
        p2.setMedication(expectedMedication);
        p2.setQuantity(1);

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Prescription p1 = new Prescription();
        p1.setMedication(new Medication());
        p1.setQuantity(1);

        Prescription p2 = new Prescription();
        p2.setMedication(new Medication());
        p2.setQuantity(1);

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Prescription original = new Prescription();
        original.setMedication(new Medication());
        original.setQuantity(1);

        String expected = "Prescription{" +
                "medication=" + original.getMedication() +
                ", quantity=" + original.getQuantity() +
                '}';
        assertEquals(expected, original.toString());
    }
}