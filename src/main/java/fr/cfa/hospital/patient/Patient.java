package fr.cfa.hospital.patient;

import fr.cfa.hospital.consultation.Consultation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("patient")
public class Patient {

    @Id
    private String id;

    @Version
    private int version;

    @Field(name = "name")
    private String name;

    @DocumentReference
    private List<Consultation> consultations;

    private String idUser;

    public Patient() {
    }

    public Patient(String id, int version, String name, List<Consultation> consultations, String idUser) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.consultations = consultations;
        this.idUser = idUser;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
                "id='" + id + '\'' +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", consultations=" + consultations +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}
