package fr.cfa.hospital.patient.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PatientDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        PatientDto original = new PatientDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getName());
    }

    @Test
    void testConstructorFull_validInput() {
        PatientDto original = new PatientDto(1L, 0, "Michel");

        assertEquals(1, original.getId());
        assertEquals(0, original.getVersion());
        assertEquals("Michel", original.getName());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        PatientDto original = new PatientDto();
        original.setId(1);
        original.setVersion(0);
        original.setName("Michel");

        String json = objectMapper.writeValueAsString(original);
        PatientDto result = objectMapper.readValue(json, PatientDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        PatientDto original = new PatientDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new PatientDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new PatientDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        PatientDto p1 = new PatientDto();
        p1.setId(1);

        PatientDto p2 = new PatientDto();
        p2.setId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentConsultationId() {
        PatientDto p1 = new PatientDto();
        p1.setName("1");

        PatientDto p2 = new PatientDto();
        p2.setName("2");

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PatientDto p1 = new PatientDto();
        p1.setId(1);
        p1.setVersion(0);
        p1.setName("Michel");

        PatientDto p2 = new PatientDto();
        p2.setId(1);
        p2.setVersion(0);
        p2.setName("Michel");

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PatientDto p1 = new PatientDto();
        p1.setId(1);
        p1.setVersion(0);
        p1.setName("Michel");

        PatientDto p2 = new PatientDto();
        p2.setId(1);
        p2.setVersion(0);
        p2.setName("Michel");

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PatientDto original = new PatientDto();
        original.setId(1);
        original.setVersion(1);
        original.setName("Michel");

        String expected = "PatientDto{" +
            "id=" + original.getId() +
            ", version=" + original.getVersion() +
            ", name='" + original.getName() + '\'' +
            '}';
        assertEquals(expected, original.toString());
    }
}