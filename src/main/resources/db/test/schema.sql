DROP TABLE IF EXISTS prescription;
DROP TABLE IF EXISTS consultation;
DROP TABLE IF EXISTS medication;
DROP TABLE IF EXISTS doctor;
DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255),
    username VARCHAR(255),
    CONSTRAINT UK_app_user_username UNIQUE (username)
);

CREATE TABLE doctor
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE patient
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE medication
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(255)
);

CREATE TABLE consultation
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    date             DATE,
    doctor_id        BIGINT NOT NULL,
    patient_id       BIGINT NOT NULL,
    file_name        VARCHAR(255),
    stored_file_name VARCHAR(255),
    CONSTRAINT FK_consultation_doctor
        FOREIGN KEY (doctor_id) REFERENCES doctor (id),
    CONSTRAINT FK_consultation_patient
        FOREIGN KEY (patient_id) REFERENCES patient (id)
);

CREATE TABLE prescription
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity        INT    NOT NULL,
    consultation_id BIGINT NOT NULL,
    medication_id   BIGINT NOT NULL,
    CONSTRAINT FK_prescription_consultation
        FOREIGN KEY (consultation_id) REFERENCES consultation (id),
    CONSTRAINT FK_prescription_medication
        FOREIGN KEY (medication_id) REFERENCES medication (id)
);

CREATE INDEX IDX_consultation_doctor ON consultation (doctor_id);
CREATE INDEX IDX_consultation_patient ON consultation (patient_id);
CREATE INDEX IDX_prescription_consultation ON prescription (consultation_id);
CREATE INDEX IDX_prescription_medication ON prescription (medication_id);