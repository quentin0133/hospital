package fr.cfa.hospital.medication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MedicationTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        Medication entity = new Medication();

        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getLabel());
        assertEquals(new ArrayList<>(), entity.getPrescriptions());
    }

    @Test
    void testConstructorFull_validInput() {
        Medication dto = new Medication(1L, "paracetamol", new ArrayList<>());

        assertEquals(1, dto.getId());
        assertEquals("paracetamol", dto.getLabel());
        assertEquals(new ArrayList<>(), dto.getPrescriptions());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        Medication original = new Medication();
        original.setId(1L);
        original.setLabel("paracetamol");
        original.setPrescriptions(new ArrayList<>());

        String json = objectMapper.writeValueAsString(original);
        Medication result = objectMapper.readValue(json, Medication.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        Medication original = new Medication();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new Medication());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new Medication());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        Medication m1 = new Medication();
        m1.setId(1L);

        Medication m2 = new Medication();
        m2.setId(2L);

        assertNotEquals(m1, m2);
    }

    @Test
    void testEquals_differentLabel() {
        Medication m1 = new Medication();
        m1.setLabel("1");

        Medication m2 = new Medication();
        m2.setLabel("2");

        assertNotEquals(m1, m2);
    }

    @Test
    void testEquals_differentPrescriptions() {
        Medication m1 = new Medication();
        m1.setPrescriptions(new ArrayList<>());

        Medication m2 = new Medication();
        m2.setPrescriptions(null);

        assertNotEquals(m1, m2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        Medication m1 = new Medication();
        m1.setId(1L);
        m1.setLabel("paracetamol");
        m1.setPrescriptions(new ArrayList<>());

        Medication m2 = new Medication();
        m2.setId(1L);
        m2.setLabel("paracetamol");
        m2.setPrescriptions(new ArrayList<>());

        assertEquals(m1, m2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Medication m1 = new Medication();
        m1.setId(1L);
        m1.setLabel("paracetamol");
        m1.setPrescriptions(new ArrayList<>());

        Medication m2 = new Medication();
        m2.setId(1L);
        m2.setLabel("paracetamol");
        m2.setPrescriptions(new ArrayList<>());

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testToString() {
        Medication original = new Medication();
        original.setId(1L);
        original.setLabel("paracetamol");
        original.setPrescriptions(new ArrayList<>());

        String expected = "Medication{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", label='" + original.getLabel() + '\'' +
                ", prescriptions=" + original.getPrescriptions() +
                '}';
        assertEquals(expected, original.toString());
    }
}