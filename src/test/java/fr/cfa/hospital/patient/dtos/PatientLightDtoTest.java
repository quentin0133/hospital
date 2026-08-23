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
class PatientLightDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        PatientLightDto original = new PatientLightDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getName());
    }

    @Test
    void testConstructorFull_validInput() {
        PatientLightDto original = new PatientLightDto(1, "Michel");

        assertEquals(1, original.getId());
        assertEquals("Michel", original.getName());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        PatientLightDto original = new PatientLightDto();
        original.setId(1);
        original.setName("Michel");

        String json = objectMapper.writeValueAsString(original);
        PatientLightDto result = objectMapper.readValue(json, PatientLightDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        PatientLightDto original = new PatientLightDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new PatientLightDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new PatientLightDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        PatientLightDto p1 = new PatientLightDto();
        p1.setId(1);

        PatientLightDto p2 = new PatientLightDto();
        p2.setId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentConsultationId() {
        PatientLightDto p1 = new PatientLightDto();
        p1.setName("1");

        PatientLightDto p2 = new PatientLightDto();
        p2.setName("2");

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        PatientLightDto p1 = new PatientLightDto();
        p1.setId(1);
        p1.setName("Michel");

        PatientLightDto p2 = new PatientLightDto();
        p2.setId(1);
        p2.setName("Michel");

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        PatientLightDto p1 = new PatientLightDto();
        p1.setId(1);
        p1.setName("Michel");

        PatientLightDto p2 = new PatientLightDto();
        p2.setId(1);
        p2.setName("Michel");

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        PatientLightDto original = new PatientLightDto();
        original.setId(1);
        original.setName("Michel");

        String expected = "PatientDto{" +
            "id=" + original.getId() +
            ", name='" + original.getName() + '\'' +
            '}';
        assertEquals(expected, original.toString());
    }
}