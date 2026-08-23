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
class MedicationGetDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    }

    @Test
    void testConstructorEmpty_validInput() {
        MedicationGetDto original = new MedicationGetDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getLabel());
    }

    @Test
    void testConstructorFull_validInput() {
        MedicationGetDto original = new MedicationGetDto(1, "paracetamol");

        assertEquals(1, original.getId());
        assertEquals("paracetamol", original.getLabel());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        MedicationGetDto original = new MedicationGetDto();
        original.setId(1);
        original.setLabel("paracetamol");

        String json = objectMapper.writeValueAsString(original);
        MedicationGetDto result = objectMapper.readValue(json, MedicationGetDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new MedicationGetDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new MedicationGetDto());
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        MedicationGetDto m1 = new MedicationGetDto();
        m1.setId(1);
        m1.setLabel("paracetamol");

        MedicationGetDto m2 = new MedicationGetDto();
        m2.setId(1);
        m2.setLabel("paracetamol");

        assertEquals(m1, m2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        MedicationGetDto m1 = new MedicationGetDto();
        m1.setId(1);
        m1.setLabel("paracetamol");

        MedicationGetDto m2 = new MedicationGetDto();
        m2.setId(1);
        m2.setLabel("paracetamol");

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testToString() {
        MedicationGetDto original = new MedicationGetDto();
        original.setId(1);
        original.setLabel("paracetamol");

        MedicationGetDto m2 = new MedicationGetDto();
        m2.setId(1);
        m2.setLabel("paracetamol");
        String expected = "MedicationDto{" +
            "id=" + original.getId() +
            ", label='" + original.getLabel() + '\'' +
            '}';
        assertEquals(expected, original.toString());
    }
}