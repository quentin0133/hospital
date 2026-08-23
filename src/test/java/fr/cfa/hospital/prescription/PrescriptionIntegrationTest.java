package fr.cfa.hospital.prescription;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.prescription.dtos.PrescriptionCommandDto;
import fr.cfa.hospital.prescription.dtos.PrescriptionLightDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PrescriptionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setup(@Value("${jwt.secret.key}") String jwtSecretKey) {
        UserDetails user = new UserSecurity(new User(1, "test", "test"));
        jwtToken = JwtUtils.generateToken(user, jwtSecretKey);
    }

    @Test
    void update() throws Exception {
        PrescriptionCommandDto request = new PrescriptionCommandDto();
        request.setId(1L);
        request.setConsultationId(1);
        request.setMedicationId(1);
        request.setQuantity(5);

        PrescriptionLightDto dto = new PrescriptionLightDto();
        dto.setId(1L);
        dto.setConsultationId(1);
        dto.setMedicationId(1);
        dto.setQuantity(5);

        mockMvc.perform(
                put("/api/prescription")
                    .header("Authorization","Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(dto));
    }

    @Test
    void update_noId() throws Exception {
        PrescriptionCommandDto request = new PrescriptionCommandDto();
        request.setId(null);
        request.setConsultationId(1);
        request.setMedicationId(1);
        request.setQuantity(5);

        mockMvc.perform(
                put("/api/prescription")
                    .header("Authorization","Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
}