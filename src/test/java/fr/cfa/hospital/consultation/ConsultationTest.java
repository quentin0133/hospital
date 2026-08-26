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
        assertNull(dto.getId());
        assertNull(dto.getDate());
        assertNull(dto.getFile());
        assertNull(dto.getDoctor());
        assertNull(dto.getPatient());
        assertEquals(new ArrayList<>(), dto.getPrescriptions());
    }

    @Test
    void testConstructorFull_validInput() {
        Patient expectedPatient = new Patient();
        Doctor expectedDoctor = new Doctor();
        File expectedFile = new File();
        Consultation dto = new Consultation(1L, 1, LocalDate.now(),
                expectedPatient, expectedDoctor, List.of(), expectedFile);

        assertEquals(1, dto.getId());
        assertEquals(LocalDate.now(), dto.getDate());
        assertEquals(expectedPatient, dto.getPatient());
        assertEquals(expectedDoctor, dto.getDoctor());
        assertEquals(new ArrayList<>(), dto.getPrescriptions());
        assertEquals(expectedFile, dto.getFile());
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
        Consultation original = new Consultation();
        original.setId(1L);
        original.setVersion(0);
        original.setDate(LocalDate.now());
        original.setPrescriptions(new ArrayList<>());
        original.setFile(new File());
        original.setPatient(new Patient());
        original.setDoctor(new Doctor());

        String expected = "Consultation{" +
                "id=" + original.getId() +
                ", version=" + original.getVersion() +
                ", date=" + original.getDate() +
                ", prescriptions=" + original.getPrescriptions() +
                ", doctor=" + original.getDoctor() +
                ", patient=" + original.getPatient() +
                ", file=" + original.getFile() +
                '}';
        assertEquals(expected, original.toString());
    }
}
