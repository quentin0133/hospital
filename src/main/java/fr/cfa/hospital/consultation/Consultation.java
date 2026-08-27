package fr.cfa.hospital.consultation;

import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.file.File;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.prescription.Prescription;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "consultation")
public class Consultation {
    @Id
    private String id;

    @Version
    private int version;

    private LocalDate date;

    private List<Prescription> prescriptions = new ArrayList<>();

    private File file;

    @DocumentReference
    private Doctor doctor;

    @DocumentReference
    private Patient patient;

    public Consultation() {
    }

    public Consultation(String id, int version, LocalDate date, Patient patient, Doctor doctor, List<Prescription> prescriptions, File file) {
        this.id = id;
        this.version = version;
        this.date = date;
        this.prescriptions = prescriptions;
        this.doctor = doctor;
        this.patient = patient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Consultation that = (Consultation) object;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", version=" + version +
                ", date=" + date +
                ", prescriptions=" + prescriptions +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", file=" + file +
                '}';
    }
}
