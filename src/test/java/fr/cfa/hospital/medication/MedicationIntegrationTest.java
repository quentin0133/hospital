package fr.cfa.hospital.medication;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.core.tools.JwtUtils;
import fr.cfa.hospital.medication.dtos.MedicationDto;
import fr.cfa.hospital.medication.dtos.MedicationPostDto;
import fr.cfa.hospital.prescription.Prescription;
import fr.cfa.hospital.prescription.PrescriptionRepository;
import jakarta.transaction.Transactional;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
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
    private PrescriptionRepository prescriptionRepository;

    private String jwtToken;

    @BeforeEach
    void setup() {
        UserDetails user = new UserSecurity(new User(1L, "test", "test"));
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
        request.setIdsConsultations(List.of(1L));

        String responseContent = mockMvc.perform(post("/api/medication")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.label").value("Smecta"))
                .andExpect(jsonPath("$.version").value(0))
                .andReturn().getResponse().getContentAsString();

        MedicationDto responseDto = objectMapper.readValue(responseContent, MedicationDto.class);
        long newMedicationId = responseDto.getId();

        Optional<Prescription> savedPrescriptionOpt = prescriptionRepository.findAll().stream()
                .filter(p -> p.getMedication().getId() == newMedicationId)
                .findFirst();

        assertTrue(savedPrescriptionOpt.isPresent());
        Prescription savedPrescription = savedPrescriptionOpt.get();
        assertEquals(newMedicationId, savedPrescription.getMedication().getId());
        assertEquals(1L, savedPrescription.getConsultation().getId());
    }

    @Test
    void update_ShouldUpdateMedication_WhenValid() throws Exception {
        Medication medication = medicationRepository.findById(2L).orElseThrow();
        int initialVersion = medication.getVersion();

        MedicationPostDto request = new MedicationPostDto();
        request.setId(2L);
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
        request.setId(3L);
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

        assertFalse(medicationRepository.existsById(6L));
    }
}