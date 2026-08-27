package fr.cfa.hospital.consultation.dtos;

import java.time.LocalDate;
import java.util.Objects;

public class ConsultationPostDto {
    private String id;

    private int version;

    private LocalDate date;

    private String doctorId;

    private String patientId;

    public ConsultationPostDto() {
    }

    public ConsultationPostDto(String id, int version, LocalDate date, String doctorId, String patientId) {
        this.id = id;
        this.version = version;
        this.date = date;
        this.doctorId = doctorId;
        this.patientId = patientId;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctor) {
        this.doctorId = doctor;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patient) {
        this.patientId = patient;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ConsultationPostDto that = (ConsultationPostDto) object;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ConsultationPostDto{" +
                "id=" + id +
                ", version=" + version +
                ", date=" + date +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                '}';
    }
}
