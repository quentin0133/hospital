package fr.cfa.hospital;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;


@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "An hospital API",
        version = "1.0",
        description = "Test"
    )
)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class HospitalApplication {

    public static void main(String[] args) {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);
        SpringApplication.run(HospitalApplication.class, args);
    }

}
