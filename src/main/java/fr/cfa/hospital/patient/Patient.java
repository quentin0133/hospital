package fr.cfa.hospital.patient;

import fr.cfa.hospital.consultation.Consultation;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @Column(name = "N_SS")
    private int id;

    @Column(name = "NomPAT")
    private String name;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;

    public Patient() {
        this.consultations = new ArrayList<>();
    }

    public Patient(int id, String name, List<Consultation> consultations) {
        this.id = id;
        this.name = name;
        this.consultations = consultations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
            Objects.equals(name, patient.name) &&
            Objects.equals(consultations, patient.consultations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, consultations);
    }

    @Override
    public String toString() {
        return "Patient{" +
            "numSS=" + id +
            ", name=" + name +
            '}';
    }
}
