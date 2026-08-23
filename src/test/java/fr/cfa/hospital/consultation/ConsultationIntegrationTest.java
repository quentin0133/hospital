package fr.cfa.hospital.consultation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.consultation.dtos.ConsultationGetDto;
import fr.cfa.hospital.doctor.Doctor;
import fr.cfa.hospital.doctor.DoctorRepository;
import fr.cfa.hospital.patient.Patient;
import fr.cfa.hospital.patient.PatientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ConsultationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    private String jwtToken;

    @BeforeEach
    void setup(@Value("${jwt.secret.key}") String jwtSecretKey) {
        UserDetails user = new UserSecurity(new User(1, "test", "test"));
        jwtToken = JwtUtils.generateToken(user, jwtSecretKey);
    }

    @AfterEach
    void cleanup(@Value("${file.storage.path}") String tempDir) throws IOException {
        FileSystemUtils.deleteRecursively(Path.of(tempDir));
    }

    @Test
    void testFindByPatientId_shouldReturn200() throws Exception {
        int idPatient = 1;
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/consultation/patient/{id}", idPatient)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$._embedded.consultationFullDtoList[0].patient.name")
                .value("Michel DUBOIS"))
            .andExpect(jsonPath("$._embedded.consultationFullDtoList[0].doctor.name")
                .value("Jean-Pierre MARTIN"));
    }

    @Test
    void testFindByPatientId_shouldReturn404WhenDoesNotExists() throws Exception {
        int idPatient = 99;
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/consultation/patient/" + idPatient)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page.number").value(0));
    }

    @Test
    void testSave_shouldReturn201() throws Exception {
        ConsultationGetDto dto = new ConsultationGetDto();
        dto.setPatientId(2L);
        dto.setDoctorId(2L);
        dto.setDate(LocalDate.of(2025, 5, 12));

        mockMvc.perform(
            post("/api/consultation")
                .header("Authorization","Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.patient.name").value("Patrique DUPONT"))
            .andExpect(jsonPath("$.doctor.name").value("Claire DUBOIS"))
            .andExpect(jsonPath("$.date").value("2025-05-12"));
    }

    @Test
    void testSave_shouldReturn400WhenWithId() throws Exception {
        ConsultationCommandDto dto = new ConsultationCommandDto();
        dto.setId(1L);
        dto.setPatientId(2L);
        dto.setDoctorId(2L);
        dto.setDate(LocalDate.of(2025, 5, 12));

        mockMvc.perform(
                post("/api/consultation")
                    .header("Authorization","Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_shouldReturn200() throws Exception {
        ConsultationCommandDto dto = new ConsultationCommandDto();
        dto.setId(1L);
        dto.setPatientId(3L);
        dto.setDoctorId(3L);
        dto.setDate(LocalDate.of(2023, 9, 1));

        mockMvc.perform(
                put("/api/consultation")
                    .header("Authorization","Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.patient.name").value("Louis TOLIER"))
            .andExpect(jsonPath("$.doctor.name").value("Philippe BERNARD"))
            .andExpect(jsonPath("$.date").value("2023-09-01"));
    }

    @Test
    void testUpdate_shouldReturn400WhenWithoutId() throws Exception {
        ConsultationCommandDto dto = new ConsultationCommandDto();
        dto.setPatientId(2L);
        dto.setDoctorId(2L);
        dto.setDate(LocalDate.of(2025, 5, 12));

        mockMvc.perform(
                put("/api/consultation")
                    .header("Authorization","Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_shouldReturn204() throws Exception {
        int id = 1;

        mockMvc.perform(
                delete("/api/consultation/" + id)
                    .header("Authorization","Bearer " + jwtToken))
            .andExpect(status().isNoContent());

        assertFalse(consultationRepository.existsById(1L));
    }

    @Test
    void testDelete_shouldReturn404WhenDoesNotExists() throws Exception {
        int id = 99;

        mockMvc.perform(
                delete("/api/consultation/" + id)
                    .header("Authorization","Bearer " + jwtToken))
            .andExpect(status().isNotFound());
    }

    @Test
    void testUploadFiles_shouldReturn400WhenEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.pdf", "application/pdf", new byte[0]
        );
        String id = "2";

        mockMvc.perform(
                multipart("/api/consultation/upload")
                    .file(file)
                    .param("id", id)
                    .with(request -> { request.setMethod("PUT"); return request; })
                    .header("Authorization","Bearer " + jwtToken))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testUploadFiles_shouldReturn400WhenNullFile() throws Exception {
        String id = "3";

        mockMvc.perform(
                multipart("/api/consultation/upload")
                    .param("id", id)
                    .with(request -> { request.setMethod("PUT"); return request; })
                    .header("Authorization","Bearer " + jwtToken))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testRetrieveFile_shouldReturn200() throws Exception {
        Consultation consultation = consultationRepository.saveAndFlush(new Consultation(
            0,
            LocalDate.now(),
            patientRepository.saveAndFlush(new Patient(0, "Michel", new ArrayList<>())),
            doctorRepository.saveAndFlush(new Doctor(0, "Bernard", new ArrayList<>())),
            new ArrayList<>(),
            null
        ));

        MockMultipartFile file = new MockMultipartFile(
            "file", "test.pdf", "application/pdf", "Hello World".getBytes()
        );

        mockMvc.perform(
                multipart("/api/consultation/upload")
                    .file(file)
                    .param("id", String.valueOf(consultation.getId()))
                    .with(request -> { request.setMethod("PUT"); return request; })
                    .header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fileName").value("test.pdf"));

        Consultation updated = consultationRepository.findById(consultation.getId()).orElseThrow();

        mockMvc.perform(get("/files/" + updated.getFile().getStoredFileName()))
            .andExpect(status().isOk())
            .andExpect(content().bytes("Hello World".getBytes())); // facultatif : vérifier le contenu
    }
}