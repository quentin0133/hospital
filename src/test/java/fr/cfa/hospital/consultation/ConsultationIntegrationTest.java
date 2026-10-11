package fr.cfa.hospital.consultation;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.consultation.dtos.ConsultationPostDto;
import fr.cfa.hospital.core.tools.JwtUtils;
import fr.cfa.hospital.utils.MongoTestSeeder;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConsultationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MongoTestSeeder mongoTestSeeder;

    private String jwtToken;

    @Value("${file.storage.path}")
    private String tempDir;

    @BeforeEach
    void setup() {
        mongoTestSeeder.resetAndSeedDatabase();
        UserDetails user = new UserSecurity(new User("user-1", "test", "test", "ROLE_ADMIN", false));
        jwtToken = jwtUtils.generateToken(user);
    }

    @AfterEach
    void cleanup() throws IOException {
        if (tempDir == null || (!tempDir.contains("target") && !tempDir.contains("test"))) {
            throw new IllegalStateException("ALERT: Attempt to delete a non-test folder : " + tempDir);
        }

        FileSystemUtils.deleteRecursively(Path.of(tempDir));
    }

    @Test
    void testFindByPatientId_shouldReturn200() throws Exception {
        String idPatient = "1";
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/consultation/patient/{id}", idPatient)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isNotEmpty())
            .andExpect(jsonPath("$.content[0].patient.name")
                .value("Michel DUBOIS"))
            .andExpect(jsonPath("$.content[0].doctor.name")
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
            .andExpect(jsonPath("$.page.totalElements").value(0));
    }

    @Test
    void testSave_shouldReturn201() throws Exception {
        ConsultationPostDto dto = new ConsultationPostDto();
        dto.setPatientId("2");
        dto.setDoctorId("2");
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
        ConsultationPostDto dto = new ConsultationPostDto();
        dto.setId("1");
        dto.setPatientId("2");
        dto.setDoctorId("2");
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
        ConsultationPostDto dto = new ConsultationPostDto();
        dto.setId("1");
        dto.setVersion(1);
        dto.setPatientId("3");
        dto.setDoctorId("3");
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
        ConsultationPostDto dto = new ConsultationPostDto();
        dto.setPatientId("2");
        dto.setDoctorId("2");
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

        assertFalse(consultationRepository.existsById("1"));
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
                    .param("idConsultation", id)
                    .with(request -> { request.setMethod("PUT"); return request; })
                    .header("Authorization","Bearer " + jwtToken))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testUploadFiles_shouldReturn400WhenNullFile() throws Exception {
        String id = "3";

        mockMvc.perform(
                multipart("/api/consultation/upload")
                    .param("idConsultation", id)
                    .with(request -> { request.setMethod("PUT"); return request; })
                    .header("Authorization","Bearer " + jwtToken))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testRetrieveFile_shouldReturn200() throws Exception {
        Consultation consultation = consultationRepository.findById("1").orElseThrow();

        MockMultipartFile file = new MockMultipartFile(
            "file", "test.pdf", "application/pdf", "Hello World".getBytes()
        );

        mockMvc.perform(
                multipart("/api/consultation/upload")
                    .file(file)
                    .param("idConsultation", consultation.getId())
                    .with(request -> { request.setMethod("PUT"); return request; })
                    .header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fileName").value("test.pdf"));

        Consultation updated = consultationRepository.findById(consultation.getId()).orElseThrow();

        mockMvc.perform(get("/files/" + updated.getFile().getStoredFileName())
                .header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isOk())
            .andExpect(content().bytes("Hello World".getBytes()));
    }
}