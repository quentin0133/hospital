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
class DoctorDtoTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testConstructorEmpty_validInput() {
        DoctorDto original = new DoctorDto();

        assertNotNull(original);
        assertEquals(0, original.getId());
        assertNull(original.getName());
    }

    @Test
    void testConstructorFull_validInput() {
        DoctorDto original = new DoctorDto(1L, 0, "Michel");

        assertEquals(1, original.getId());
        assertEquals("Michel", original.getName());
    }

    @Test
    void testJacksonSerialization_validInput() throws Exception {
        DoctorDto original = new DoctorDto();
        original.setId(1);
        original.setName("Michel");

        String json = objectMapper.writeValueAsString(original);
        DoctorDto result = objectMapper.readValue(json, DoctorDto.class);

        assertEquals(original, result);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameInstance() {
        DoctorDto original = new DoctorDto();
        assertEquals(original, original);
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new DoctorDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new DoctorDto());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenDifferentId() {
        DoctorDto p1 = new DoctorDto();
        p1.setId(1);

        DoctorDto p2 = new DoctorDto();
        p2.setId(2);

        assertNotEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenDifferentConsultationId() {
        DoctorDto p1 = new DoctorDto();
        p1.setName("1");

        DoctorDto p2 = new DoctorDto();
        p2.setName("2");

        assertEquals(p1, p2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        DoctorDto p1 = new DoctorDto();
        p1.setId(1);
        p1.setName("Michel");

        DoctorDto p2 = new DoctorDto();
        p2.setId(1);
        p2.setName("Michel");

        assertEquals(p1, p2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        DoctorDto p1 = new DoctorDto();
        p1.setId(1);
        p1.setName("Michel");

        DoctorDto p2 = new DoctorDto();
        p2.setId(1);
        p2.setName("Michel");

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        DoctorDto original = new DoctorDto();
        original.setId(1);
        original.setVersion(1);
        original.setName("Michel");

        String expected = "DoctorDto{" +
            "id=" + original.getId() +
            ", version=" + original.getVersion() +
            ", name='" + original.getName() + '\'' +
            '}';
        assertEquals(expected, original.toString());
    }
}