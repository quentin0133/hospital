package fr.cfa.hospital.doctor;

import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.doctor.dtos.DoctorLightDto;
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
class DoctorIntegrationTest {
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
            get("/api/doctor")
                .header("Authorization","Bearer " + jwtToken)
                .accept("application/json")
                .param("page", page)
                .param("size", pageSize))
        .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.doctorLightDtoList[0].name")
                .value("Jean-Pierre MARTIN"))
            .andExpect(jsonPath("$._embedded.doctorLightDtoList[1].name")
                .value("Claire DUBOIS"));
    }

    @Test
    void findByName() throws Exception {
        String name = "Jean-Pierre MARTIN";
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/doctor/name/{name}", name)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json")
                    .param("page", page)
                    .param("size", pageSize))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.doctorLightDtoList[0].name")
                .value("Jean-Pierre MARTIN"));
    }

    @Test
    void findByName_empty() throws Exception {
        String name = "fqsdfgq sdgqsdgqsfd sdgqsdg";
        String page = "0";
        String pageSize = "10";

        mockMvc.perform(
                get("/api/doctor/name/{name}", name)
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
        DoctorLightDto expected = new DoctorLightDto(1, "Jean-Pierre MARTIN");

        mockMvc.perform(
                get("/api/doctor/{id}", id)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(expected));
    }

    @Test
    void findById_notFound() throws Exception {
        int id = 99;

        mockMvc.perform(
                get("/api/doctor/{id}", id)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json"))
            .andExpect(status().isNotFound());
    }
}