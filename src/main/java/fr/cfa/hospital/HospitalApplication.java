package fr.cfa.hospital;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;


@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Hospital Management API",
        version = "1.0.0",
        description = """
            API REST de gestion du système hospitalier.

            ### Périmètre fonctionnel
            * Gestion des dossiers patients (CRUD, pagination, filtres dynamiques).
            * Administration du personnel soignant et des médecins.
            * Suivi des consultations médicales et téléversement sécurisé de pièces jointes.
            * Gestion du catalogue de médicaments et délivrance d'ordonnances.

            ### Sécurité & Authentification
            * Accès sécurisé par jeton porteur **JWT (Bearer Token)**.
            * Authentifiez-vous sur `/api/auth/login` puis renseignez le jeton via le bouton **Authorize** ci-dessus.
            """,
        contact = @Contact(
            name = "Support Technique - Hospital API",
            email = "support@hospital.cfa.fr"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Environnement local de développement"
        )
    },
    security = {
        @SecurityRequirement(name = "bearerAuth")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Renseignez votre jeton JWT (sans préfixe 'Bearer ' manuel)."
)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class HospitalApplication {

    public static void main(String[] args) {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);
        SpringApplication.run(HospitalApplication.class, args);
    }

}
