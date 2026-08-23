package fr.cfa.hospital.doctor;

import fr.cfa.hospital.consultation.Consultation;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @Column(name = "Matricule")
    private int id;

    @Column(name = "NomMED")
    private String name;

    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations;

    public Doctor() {
        this.consultations = new ArrayList<>();
    }

    public Doctor(int id, String name, List<Consultation> consultations) {
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
        Doctor doctor = (Doctor) o;
        return id == doctor.id &&
            Objects.equals(name, doctor.name) &&
            Objects.equals(consultations, doctor.consultations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, consultations);
    }

    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
