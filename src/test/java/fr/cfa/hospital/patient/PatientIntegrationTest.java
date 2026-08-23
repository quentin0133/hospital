package fr.cfa.hospital.patient;

import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.patient.dtos.PatientLightDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PatientIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private String jwtToken;

    @BeforeEach
    void setup(@Value("${jwt.secret.key}") String jwtSecretKey) {
        UserDetails user = new UserSecurity(new User(1, "test", "test"));
        jwtToken = JwtUtils.generateToken(user, jwtSecretKey);
    }

    @Test
    void findAll() throws Exception {
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/patient")
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$._embedded.patientLightDtoList[0].name")
                .value("Michel DUBOIS"))
            .andExpect(jsonPath("$._embedded.patientLightDtoList[1].name")
                .value("Patrique DUPONT"));
    }

    @Test
    void findByName() throws Exception {
        String name = "e";
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/patient/name/{name}", name)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$._embedded.patientLightDtoList[0].name")
                .value("Michel DUBOIS"))
            .andExpect(jsonPath("$._embedded.patientLightDtoList[1].name")
                .value("Patrique DUPONT"));
    }

    @Test
    void findByName_specific() throws Exception {
        String name = "Patrique DUPONT";
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/patient/name/{name}", name)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$._embedded.patientLightDtoList[0].name")
                .value("Patrique DUPONT"));
    }

    @Test
    void findByName_notExist() throws Exception {
        String name = "qsdfqdsfqsd fqsdf";
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/patient/name/{name}", name)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page.number").value(0));
    }

    @Test
    void findById() throws Exception {
        int id = 1;
        PatientLightDto expected = new PatientLightDto(1, "Michel DUBOIS");

        mockMvc.perform(
                get("/api/patient/{id}", id)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(expected));
    }

    @Test
    void findById_notFound() throws Exception {
        int id = 99;

        mockMvc.perform(
                get("/api/patient/{id}", id)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json"))
            .andExpect(status().isNotFound());
    }
}