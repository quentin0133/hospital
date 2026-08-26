package fr.cfa.hospital.doctor;

import fr.cfa.hospital.consultation.Consultation;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serial_id")
    private Long id;

    @Version
    @ColumnDefault("0")
    private int version;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations;

    public Doctor() {
        this.consultations = new ArrayList<>();
    }

    public Doctor(Long id, int version, String name, List<Consultation> consultations) {
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
        Doctor that = (Doctor) object;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", consultations=" + consultations +
                '}';
    }
}
