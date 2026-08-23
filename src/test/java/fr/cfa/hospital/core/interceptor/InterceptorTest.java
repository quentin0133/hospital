package fr.cfa.hospital.core.interceptor;

import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InterceptorTest {
    @Autowired
    private MockMvc mockMvc;

    private String jwtToken;

    @BeforeEach
    void setup(@Value("${jwt.secret.key}") String jwtSecretKey) {
        UserDetails user = new UserSecurity(new User(1, "test", "test"));
        jwtToken = JwtUtils.generateToken(user, jwtSecretKey);
    }

    @Test
    void pathNotExisting_notConnected() throws Exception {
        mockMvc.perform(
                get("/sqdfqsdf"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void pathExisting_notConnected() throws Exception {
        mockMvc.perform(
                get("/api/consultation"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void pathNotExisting_connected() throws Exception {
        mockMvc.perform(
                get("/sqdfqsdf")
                    .header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isNotFound());
    }
}