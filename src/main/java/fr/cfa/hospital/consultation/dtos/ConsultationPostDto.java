package fr.cfa.hospital.consultation.dtos;

import java.time.LocalDate;
import java.util.Objects;

public class ConsultationPostDto {
    private Long id;

    private int version;

    private LocalDate date;

    private long doctorId;

    private long patientId;

    public ConsultationPostDto() {
    }

    public ConsultationPostDto(Long id, int version, LocalDate date, long doctorId, long patientId) {
        this.id = id;
        this.version = version;
        this.date = date;
        this.doctorId = doctorId;
        this.patientId = patientId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctor) {
        this.doctorId = doctor;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patient) {
        this.patientId = patient;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ConsultationPostDto that = (ConsultationPostDto) object;
        return Objects.equals(id, that.id) && version == that.version && doctorId == that.doctorId && patientId == that.patientId && Objects.equals(date, that.date);
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
