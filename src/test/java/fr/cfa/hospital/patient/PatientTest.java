package fr.cfa.hospital.patient;

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
class PatientTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        Patient original = new Patient();

        assertNotNull(original);
        assertNull(original.getId());
        assertNull(original.getName());
        assertEquals(new ArrayList<>(), original.getConsultations());
    }

    @Test
    void testConstructorFull_validInput() {
        Patient original = new Patient(1L, 1, "Michel", new ArrayList<>());

        assertNotNull(original);
        assertEquals(1, original.getId());
        assertEquals("Michel", original.getName());
        assertEquals(new ArrayList<>(), original.getConsultations());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        Patient original = new Patient();
        original.setId(1L);
        original.setName("Michel");
        original.setConsultations(new ArrayList<>());

        String json = objectMapper.writeValueAsString(original);
        Patient result = objectMapper.readValue(json, Patient.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        Patient original = new Patient();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new Patient());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new Patient());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        Patient p1 = new Patient();
        p1.setId(1L);

        Patient p2 = new Patient();
        p2.setId(2L);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentName() {
        Patient p1 = new Patient();
        p1.setName("1");

        Patient p2 = new Patient();
        p2.setName("2");

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_differentConsultations() {
        Patient p1 = new Patient();
        p1.setConsultations(new ArrayList<>());

        Patient p2 = new Patient();
        p2.setConsultations(null);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        Patient p1 = new Patient();
        p1.setId(1L);
        p1.setName("Michel");
        p1.setConsultations(new ArrayList<>());

        Patient p2 = new Patient();
        p2.setId(1L);
        p2.setName("Michel");
        p2.setConsultations(new ArrayList<>());

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Patient p1 = new Patient();
        p1.setId(1L);
        p1.setName("Michel");
        p1.setConsultations(new ArrayList<>());

        Patient p2 = new Patient();
        p2.setId(1L);
        p2.setName("Michel");
        p2.setConsultations(new ArrayList<>());

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Patient original = new Patient();
        original.setId(1L);
        original.setName("Michel");
        original.setConsultations(new ArrayList<>());

        String expected = "Patient{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", name='" + original.getName() + '\'' +
                ", consultations=" + original.getConsultations() +
                '}';
        assertEquals(expected, original.toString());
    }
}