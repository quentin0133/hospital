package fr.cfa.hospital.doctor.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DoctorLightDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        DoctorLightDto original = new DoctorLightDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getName());
    }

    @Test
    void testConstructorFull_validInput() {
        DoctorLightDto original = new DoctorLightDto(1, "Michel");

        assertEquals(1, original.getId());
        assertEquals("Michel", original.getName());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        DoctorLightDto original = new DoctorLightDto();
        original.setId(1);
        original.setName("Michel");

        String json = objectMapper.writeValueAsString(original);
        DoctorLightDto result = objectMapper.readValue(json, DoctorLightDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        DoctorLightDto original = new DoctorLightDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new DoctorLightDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new DoctorLightDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        DoctorLightDto p1 = new DoctorLightDto();
        p1.setId(1);

        DoctorLightDto p2 = new DoctorLightDto();
        p2.setId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentConsultationId() {
        DoctorLightDto p1 = new DoctorLightDto();
        p1.setName("1");

        DoctorLightDto p2 = new DoctorLightDto();
        p2.setName("2");

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        DoctorLightDto p1 = new DoctorLightDto();
        p1.setId(1);
        p1.setName("Michel");

        DoctorLightDto p2 = new DoctorLightDto();
        p2.setId(1);
        p2.setName("Michel");

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        DoctorLightDto p1 = new DoctorLightDto();
        p1.setId(1);
        p1.setName("Michel");

        DoctorLightDto p2 = new DoctorLightDto();
        p2.setId(1);
        p2.setName("Michel");

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        DoctorLightDto original = new DoctorLightDto();
        original.setId(1);
        original.setName("Michel");

        String expected = "DoctorDto{" +
            "id=" + original.getId() +
            ", name='" + original.getName() + '\'' +
            '}';
        assertEquals(expected, original.toString());
    }
}