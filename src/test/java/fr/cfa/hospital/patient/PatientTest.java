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
        assertEquals(0, original.getVersion());
        assertNull(original.getName());
        assertEquals(new ArrayList<>(), original.getConsultations());
        assertNull(original.getIdUser());
    }

    @Test
    void testConstructorFull_validInput() {
        Patient original = new Patient("1", 1, "Michel", new ArrayList<>(), null);

        assertNotNull(original);
        assertEquals("1", original.getId());
        assertEquals("Michel", original.getName());
        assertEquals(new ArrayList<>(), original.getConsultations());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        Patient original = new Patient();
        original.setId("1");
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
        p1.setId("1");

        Patient p2 = new Patient();
        p2.setId("2");

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenIdNull() {
        Patient p1 = new Patient();
        p1.setId(null);

        Patient p2 = new Patient();
        p2.setId(null);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentName() {
        Patient p1 = new Patient();
        p1.setId("1");
        p1.setName("1");

        Patient p2 = new Patient();
        p2.setId("1");
        p2.setName("2");

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentConsultations() {
        Patient p1 = new Patient();
        p1.setId("1");
        p1.setConsultations(new ArrayList<>());

        Patient p2 = new Patient();
        p2.setId("1");
        p2.setConsultations(null);

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentIdUser() {
        Patient p1 = new Patient();
        p1.setId("1");
        p1.setIdUser("1");

        Patient p2 = new Patient();
        p2.setId("1");
        p2.setIdUser("2");

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        Patient p1 = new Patient();
        p1.setId("1");
        p1.setVersion(1);
        p1.setName("Michel");
        p1.setConsultations(new ArrayList<>());
        p1.setIdUser("5");

        Patient p2 = new Patient();
        p2.setId("1");
        p2.setVersion(1);
        p2.setName("Michel");
        p2.setConsultations(new ArrayList<>());
        p2.setIdUser("5");

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Patient p1 = new Patient();
        p1.setId("1");
        p1.setVersion(1);
        p1.setName("Michel");
        p1.setConsultations(new ArrayList<>());
        p1.setIdUser("5");

        Patient p2 = new Patient();
        p2.setId("1");
        p2.setVersion(1);
        p2.setName("Michel");
        p2.setConsultations(new ArrayList<>());
        p2.setIdUser("5");

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Patient original = new Patient();
        original.setId("1");
        original.setName("Michel");
        original.setConsultations(new ArrayList<>());

        String expected = "Patient{" +
                "id='" + original.getId() + '\'' +
                ", version=" + original.getVersion() +
                ", name='" + original.getName() + '\'' +
                ", consultations=" + original.getConsultations() +
                ", idUser='" + original.getIdUser() + '\'' +
                '}';
        assertEquals(expected, original.toString());
    }
}