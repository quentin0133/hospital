package fr.cfa.hospital.core.runner;

import fr.cfa.hospital.auth.user.User;
import fr.cfa.hospital.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Value("${app.default.username}")
    private String defaultUsername;

    @Value("${app.default.password}")
    private String defaultPassword;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DataSeeder(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername(defaultUsername)) {
            User user = new User();

            user.setUsername(defaultUsername);
            user.setPassword(passwordEncoder.encode(defaultPassword));

            userRepository.save(user);
        }
    }
}
