package fr.cfa.hospital.medication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.cfa.hospital.prescription.Prescription;
import jakarta.persistence.*;

@Entity
@Table(name = "MEDICAMENT")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Code")
    private int id;

    @Column(name = "Libellé")
    private String label;

    @OneToMany(mappedBy = "medication")
    private List<Prescription> prescriptions;

    public Medication() {
        this.prescriptions = new ArrayList<>();
    }

    public Medication(int id, String label, List<Prescription> prescriptions) {
        this.id = id;
        this.label = label;
        this.prescriptions = prescriptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == that.id &&
            Objects.equals(label, that.label) &&
            Objects.equals(prescriptions, that.prescriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, prescriptions);
    }

    @Override
    public String toString() {
        return "Medication{" +
            "id=" + id +
            ", label='" + label + '\'' +
            ", prescriptions=" + prescriptions +
            '}';
    }
}