package fr.cfa.hospital.consultation;

import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.file.File;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.prescription.Prescription;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "consultation")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Long id;

    @Version
    @ColumnDefault("0")
    private int version;

    private LocalDate date;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL)
    private File file;

    public Consultation() {
    }

    public Consultation(Long id, int version, LocalDate date, Patient patient, Doctor doctor, List<Prescription> prescriptions, File file) {
        this.id = id;
        this.version = version;
        this.date = date;
        this.prescriptions = prescriptions;
        this.doctor = doctor;
        this.patient = patient;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long number) {
        this.id = number;
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
