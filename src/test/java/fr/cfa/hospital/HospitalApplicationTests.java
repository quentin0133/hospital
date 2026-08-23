package fr.cfa.hospital;

import fr.cfa.hospital.HospitalApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = HospitalApplication.class)
@ActiveProfiles("test")
class HospitalApplicationTests {

    @Test
    void contextLoads() {
    }
}
