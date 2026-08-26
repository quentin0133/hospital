package fr.cfa.hospital.doctor;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.consultation.Consultation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DoctorTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        Doctor dto = new Doctor();

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertEquals(new ArrayList<>(), dto.getConsultations());
    }

    @Test
    void testConstructorFull_validInput() {
        Doctor dto = new Doctor(1L, 1, "Jean pierre", new ArrayList<>());

        assertEquals(1, dto.getId());
        assertEquals("Jean pierre", dto.getName());
        assertEquals(new ArrayList<>(), dto.getConsultations());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        Doctor original = new Doctor();
        original.setId(1L);
        original.setName("Jean pierre");
        original.setConsultations(new ArrayList<>());

        String json = objectMapper.writeValueAsString(original);
        Doctor result = objectMapper.readValue(json, Doctor.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        Doctor original = new Doctor();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new Doctor());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new Doctor());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        Doctor d1 = new Doctor(1L, 1, "Jean pierre", new ArrayList<>());
        Doctor d2 = new Doctor(2L, 1, "Jean pierre", new ArrayList<>());

        assertNotEquals(d1, d2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentName() {
        Doctor d1 = new Doctor(1L, 1, "Jean pierre", new ArrayList<>());
        Doctor d2 = new Doctor(1L, 1, "Marc", new ArrayList<>());

        assertEquals(d1, d2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentConsultations() {
        ArrayList<Consultation> list1 = new ArrayList<>();
        Consultation consultation1 = new Consultation();
        consultation1.setId(1L);
        list1.add(consultation1);

        ArrayList<Consultation> list2 = new ArrayList<>();
        Consultation consultation2 = new Consultation();
        consultation1.setId(2L);
        list2.add(consultation2);

        Doctor d1 = new Doctor(1L, 1, "Jean pierre", list1);
        Doctor d2 = new Doctor(1L, 1, "Jean pierre", list2);

        assertEquals(d1, d2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setName("Jean pierre");
        d1.setConsultations(new ArrayList<>());

        Doctor d2 = new Doctor();
        d2.setId(1L);
        d2.setName("Jean pierre");
        d2.setConsultations(new ArrayList<>());

        assertEquals(d1, d2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setName("Jean pierre");
        d1.setConsultations(new ArrayList<>());

        Doctor d2 = new Doctor();
        d2.setId(1L);
        d2.setName("Jean pierre");
        d2.setConsultations(new ArrayList<>());

        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void testToString() {
        Doctor original = new Doctor();
        original.setId(1L);
        original.setName("Jean pierre");
        original.setConsultations(new ArrayList<>());

        String expected = "Doctor{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", name='" + original.getName() + '\'' +
                ", consultations=" + original.getConsultations() +
                '}';
        assertEquals(expected, original.toString());
    }
}