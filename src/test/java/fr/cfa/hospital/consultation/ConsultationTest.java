package fr.cfa.hospital.consultation;

import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.file.File;
import fr.cfa.hospital.patient.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ConsultationTest {

    @Test
    void testConstructorEmpty_validInput() {
        Consultation dto = new Consultation();

        assertNotNull(dto);
        assertNull(dto.getDate());
        assertNull(dto.getFile());
        assertNull(dto.getDoctor());
        assertNull(dto.getPatient());
        assertEquals(new ArrayList<>(), dto.getPrescriptions());
        assertEquals(0, dto.getId());
    }

    @Test
    void testConstructorFull_validInput() {
        Consultation dto = new Consultation(1, LocalDate.now(),
            new Patient(), new Doctor(), List.of(), new File());

        assertEquals(1, dto.getId());
        assertEquals(LocalDate.now(), dto.getDate());
        assertEquals(new Patient(), dto.getPatient());
        assertEquals(new Doctor(), dto.getDoctor());
        assertEquals(new ArrayList<>(), dto.getPrescriptions());
        assertEquals(new File(), dto.getFile());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNotSameClass() {
        assertNotEquals(new Object(), new Consultation());
    }

    @Test
    void testEquals_shouldNotBeEqualsWhenNull() {
        assertNotEquals(null, new Consultation());
    }

    @Test
    void testNotEquals_shouldNotEqualsWhenDifferentId() {
        Consultation c1 = new Consultation();
        c1.setId(1L);

        Consultation c2 = new Consultation();
        c2.setId(2L);

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_shouldNotEqualsWhenDifferentDate() {
        Consultation c1 = new Consultation();
        c1.setDate(LocalDate.now());

        Consultation c2 = new Consultation();
        c2.setDate(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_shouldNotEqualsWhenDifferentPrescriptions() {
        Consultation c1 = new Consultation();
        c1.setPrescriptions(new ArrayList<>());

        Consultation c2 = new Consultation();
        c2.setPrescriptions(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_shouldNotEqualsWhenDifferentFile() {
        Consultation c1 = new Consultation();
        c1.setFile(new File());

        Consultation c2 = new Consultation();
        c2.setFile(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_shouldNotEqualsWhenDifferentPatient() {
        Consultation c1 = new Consultation();
        c1.setPatient(new Patient());

        Consultation c2 = new Consultation();
        c2.setPatient(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testNotEquals_shouldNotEqualsWhenDifferentDoctor() {
        Consultation c1 = new Consultation();
        c1.setDoctor(new Doctor());

        Consultation c2 = new Consultation();
        c2.setDoctor(null);

        assertNotEquals(c1, c2);
    }

    @Test
    void testEquals_shouldBeEqualsWhenSameProperties() {
        Consultation c1 = new Consultation();
        c1.setId(1L);
        c1.setDate(LocalDate.now());
        c1.setPrescriptions(new ArrayList<>());
        c1.setFile(new File());
        c1.setPatient(new Patient());
        c1.setDoctor(new Doctor());

        Consultation c2 = new Consultation();
        c2.setId(1L);
        c2.setDate(LocalDate.now());
        c2.setPrescriptions(new ArrayList<>());
        c2.setFile(new File());
        c2.setPatient(new Patient());
        c2.setDoctor(new Doctor());

        assertEquals(c1, c2);
    }

    @Test
    void testHashCode_shouldBeEqualsWhenSameProperties() {
        Consultation c1 = new Consultation();
        c1.setId(1L);
        c1.setDate(LocalDate.now());
        c1.setPrescriptions(new ArrayList<>());
        c1.setFile(new File());
        c1.setPatient(new Patient());
        c1.setDoctor(new Doctor());

        Consultation c2 = new Consultation();
        c2.setId(1L);
        c2.setDate(LocalDate.now());
        c2.setPrescriptions(new ArrayList<>());
        c2.setFile(new File());
        c2.setPatient(new Patient());
        c2.setDoctor(new Doctor());

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        Consultation consultation = new Consultation(
            1, LocalDate.now(), new Patient(), new Doctor(), List.of(), new File()
        );

        String expected = "Consultation{" +
            "id=" + consultation.getId() +
            ", date=" + consultation.getDate() +
            ", prescriptions=" + consultation.getPrescriptions() +
            ", file=" + consultation.getFile() +
            '}';
        assertEquals(expected, consultation.toString());
    }
}
