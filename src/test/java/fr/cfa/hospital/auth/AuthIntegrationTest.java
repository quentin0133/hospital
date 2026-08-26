package fr.cfa.hospital.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.HospitalApplication;
import fr.cfa.hospital.auth.dtos.LoginPostDto;
import fr.cfa.hospital.auth.user.dtos.UserDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAuthenticate_shouldReturn200WhenGoodCredentials() throws Exception {
        LoginPostDto login = new LoginPostDto("test", "test");
        UserDto expected = new UserDto(1, "test");

        mockMvc.perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user").value(expected))
            .andExpect(jsonPath("$.user.id").value(1))
            .andExpect(jsonPath("$.user.username").value("test"));
    }

    @Test
    void testAuthenticate_shouldReturn401WhenBadCredentials() throws Exception {
        LoginPostDto login = new LoginPostDto();
        login.setUsername("qsdfqfs");
        login.setPassword("qsfsqd");

        mockMvc.perform(
                post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isForbidden());
    }
}