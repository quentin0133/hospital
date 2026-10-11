package fr.cfa.hospital.medication;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.UserRepository;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.consultation.ConsultationRepository;
import fr.cfa.hospital.core.tools.JwtUtils;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import fr.cfa.hospital.utils.MongoTestSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MedicationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTestSeeder mongoTestSeeder;

    private String jwtToken;

    @BeforeEach
    void setup() {
        mongoTestSeeder.resetAndSeedDatabase();
        UserDetails user = new UserSecurity(userRepository.findByUsername("test").orElseThrow());
        jwtToken = jwtUtils.generateToken(user);
    }

    @Test
    void findById_ShouldReturnMedication_WhenExists() throws Exception {
        mockMvc.perform(get("/api/medication/id/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.label").value("Doliprane"));
    }

    @Test
    void findById_ShouldReturn404_WhenNotExists() throws Exception {
        mockMvc.perform(get("/api/medication/id/999")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void save_ShouldCreateAndReturnMedication() throws Exception {
        MedicationPostDto request = new MedicationPostDto();
        request.setLabel("Smecta");

        mockMvc.perform(post("/api/medication")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.label").value("Smecta"))
                .andExpect(jsonPath("$.version").value(1))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void update_ShouldUpdateMedication_WhenValid() throws Exception {
        Medication medication = medicationRepository.findById("2").orElseThrow();
        int initialVersion = medication.getVersion();

        MedicationPostDto request = new MedicationPostDto();
        request.setId("2");
        request.setVersion(initialVersion);
        request.setLabel("Efferalgan Modifié");

        mockMvc.perform(put("/api/medication")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("Efferalgan Modifié"))
                .andExpect(jsonPath("$.version").value(initialVersion + 1));
    }

    @Test
    void update_ShouldReturn409_WhenVersionConflict() throws Exception {
        MedicationPostDto request = new MedicationPostDto();
        request.setId("3");
        request.setVersion(99);
        request.setLabel("Attempt of scrambled time");

        mockMvc.perform(put("/api/medication")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteById_ShouldRemoveMedication() throws Exception {
        mockMvc.perform(delete("/api/medication/6")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        assertFalse(medicationRepository.existsById("6L"));
    }
}