package fr.cfa.hospital.doctor;

import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.core.tools.JwtUtils;
import fr.cfa.hospital.doctor.dtos.DoctorDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JwtUtils jwtUtils;

    private String jwtToken;

    @BeforeEach
    void setup() {
        UserDetails user = new UserSecurity(new User(1L, "test", "test"));
        jwtToken = jwtUtils.generateToken(user);
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
            .andExpect(jsonPath("$.content[0].name")
                .value("Jean-Pierre MARTIN"))
            .andExpect(jsonPath("$.content[1].name")
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
            .andExpect(jsonPath("$.content[0].name")
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
            .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void findById() throws Exception {
        int id = 1;
        DoctorDto expected = new DoctorDto(1L, 0, "Jean-Pierre MARTIN");

        mockMvc.perform(
                get("/api/doctor/id/{id}", id)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(expected));
    }

    @Test
    void findById_notFound() throws Exception {
        int id = 99;

        mockMvc.perform(
                get("/api/doctor/id/{id}", id)
                    .header("Authorization","Bearer " + jwtToken)
                    .accept("application/json"))
            .andExpect(status().isNotFound());
    }
}