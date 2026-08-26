package fr.cfa.hospital.patient;

import fr.cfa.hospital.consultation.Consultation;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ss_number")
    private Long id;

    @Version
    @ColumnDefault("0")
    private int version;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;

    public Patient() {
        this.consultations = new ArrayList<>();
    }

    public Patient(Long id, int version, String name, List<Consultation> consultations) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.consultations = consultations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Patient that = (Patient) object;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", consultations=" + consultations +
                '}';
    }
}
