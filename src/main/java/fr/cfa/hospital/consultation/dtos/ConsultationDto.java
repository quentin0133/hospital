package fr.cfa.hospital.consultation.dtos;

import fr.cfa.hospital.doctor.dtos.DoctorDto;
import fr.cfa.hospital.file.dtos.FileDto;
import fr.cfa.hospital.patient.dtos.PatientDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDto {
    private String id;

    private int version;

    private DoctorDto doctor;

    private PatientDto patient;

    private List<PrescriptionDto> prescriptions;

    private LocalDate date;

    private FileDto file;

    public ConsultationDto() {
        prescriptions = new ArrayList<>();
    }

    public ConsultationDto(String id, int version, LocalDate date, PatientDto patient, DoctorDto doctor, List<PrescriptionDto> prescriptions, FileDto file) {
        this.id = id;
        this.version = version;
        this.doctor = doctor;
        this.patient = patient;
        this.prescriptions = prescriptions;
        this.date = date;
        this.file = file;
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

    public DoctorDto getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDto doctor) {
        this.doctor = doctor;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public List<PrescriptionDto> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionDto> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public FileDto getFile() {
        return file;
    }

    public void setFile(FileDto file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ConsultationDto that = (ConsultationDto) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ConsultationDto{" +
                "id=" + id +
                ", version=" + version +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", prescriptions=" + prescriptions +
                ", date=" + date +
                ", file=" + file +
                '}';
    }
}
