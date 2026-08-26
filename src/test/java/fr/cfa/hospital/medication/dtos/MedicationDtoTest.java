package fr.cfa.hospital.medication.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MedicationDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    }

    @Test
    void testConstructorEmpty_validInput() {
        MedicationDto original = new MedicationDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getLabel());
    }

    @Test
    void testConstructorFull_validInput() {
        MedicationDto original = new MedicationDto(1, 1, "paracetamol");

        assertEquals(1, original.getId());
        assertEquals("paracetamol", original.getLabel());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        MedicationDto original = new MedicationDto();
        original.setId(1);
        original.setLabel("paracetamol");

        String json = objectMapper.writeValueAsString(original);
        MedicationDto result = objectMapper.readValue(json, MedicationDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new MedicationDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new MedicationDto());
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        MedicationDto m1 = new MedicationDto();
        m1.setId(1);
        m1.setLabel("paracetamol");

        MedicationDto m2 = new MedicationDto();
        m2.setId(1);
        m2.setLabel("paracetamol");

        assertEquals(m1, m2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        MedicationDto m1 = new MedicationDto();
        m1.setId(1);
        m1.setLabel("paracetamol");

        MedicationDto m2 = new MedicationDto();
        m2.setId(1);
        m2.setLabel("paracetamol");

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testToString() {
        MedicationDto original = new MedicationDto();
        original.setId(1);
        original.setLabel("paracetamol");

        String expected = "MedicationDto{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", label='" + original.getLabel() + '\'' +
                '}';
        assertEquals(expected, original.toString());
    }
}