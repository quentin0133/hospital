package fr.cfa.hospital.core.interceptor;

import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserSecurity;
import fr.cfa.hospital.core.tools.JwtUtils;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InterceptorTest {
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
    void pathNotExisting_notConnected() throws Exception {
        mockMvc.perform(
                get("/sqdfqsdf"))
            .andExpect(status().isForbidden());
    }

    @Test
    void pathExisting_notConnected() throws Exception {
        mockMvc.perform(
                get("/api/consultation"))
            .andExpect(status().isForbidden());
    }

    @Test
    void pathNotExisting_connected() throws Exception {
        mockMvc.perform(
                get("/sqdfqsdf")
                    .header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isNotFound());
    }
}