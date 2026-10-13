package fr.cfa.hospital.core.runner;

import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Database seeder executed upon application startup.
 * Automatically provisions an initial default administrator account if none exists,
 * active in all environments except during test execution.
 */
@Component
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    /**
     * Default administrator username loaded from application properties.
     */
    @Value("${app.default.username}")
    private String defaultUsername;

    /**
     * Default administrator raw password loaded from application properties.
     */
    @Value("${app.default.password}")
    private String defaultPassword;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * Constructs the data seeder with required security and persistence dependencies.
     *
     * @param passwordEncoder encoder used to hash passwords before persistence
     * @param userRepository  repository used to check and persist user accounts
     */
    public DataSeeder(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Executes the seed logic on application startup.
     * Verifies whether the default administrator user exists; if missing, hashes credentials
     * and saves a new user with the {@code ROLE_ADMIN} authority.
     *
     * @param args incoming command-line arguments
     */
    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername(defaultUsername)) {
            User user = new User();

            user.setUsername(defaultUsername);
            user.setPassword(passwordEncoder.encode(defaultPassword));
            user.setRole("ROLE_ADMIN");

            userRepository.save(user);
        }
    }
}