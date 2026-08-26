package fr.cfa.hospital.medication;

import fr.cfa.hospital.prescription.Prescription;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private Long id;

    @Version
    @ColumnDefault("0")
    private int version;

    @Column(name = "label")
    private String label;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions;

    public Medication() {
        this.prescriptions = new ArrayList<>();
    }

    public Medication(Long id, String label, List<Prescription> prescriptions) {
        this.id = id;
        this.label = label;
        this.prescriptions = prescriptions;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", version=" + version +
                ", label='" + label + '\'' +
                ", prescriptions=" + prescriptions +
                '}';
    }
}