package fr.cfa.hospital.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cfa.hospital.core.interceptor.ExceptionHandlerFilter;
import fr.cfa.hospital.core.interceptor.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Security configuration for the application.
 * Manages HTTP security filters, CORS policies, JWT authentication integration,
 * and endpoint authorization rules.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Public endpoints accessible without authentication regardless of HTTP method.
     */
    private static final String[] AUTHORIZED_URLS = new String[]{
        "/api/auth/login",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**"
    };

    /**
     * Method-specific public endpoints.
     */
    private static final Map<HttpMethod, String[]> AUTHORIZED_URLS_BY_METHOD = Map.of(
        HttpMethod.GET, new String[]{},
        HttpMethod.POST, new String[]{},
        HttpMethod.OPTIONS, new String[]{"/**"}
    );

    /**
     * CORS preflight response cache duration (1 hour in seconds).
     */
    private static final int EXPIRATION_TIME_SECONDS = 60 * 60;

    private final ObjectMapper objectMapper;
    private final JwtAuthFilter jwtAuthFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final UserDetailsService userDetailsService;

    /**
     * Constructs the security configuration with required dependencies.
     *
     * @param objectMapper           JSON serializer for security error responses
     * @param jwtAuthFilter          custom filter validating incoming JWT bearer tokens
     * @param exceptionHandlerFilter filter catching exceptions raised during filter chain execution
     * @param userDetailsService     service loading user-specific data during authentication
     */
    public SecurityConfig(ObjectMapper objectMapper,
                          JwtAuthFilter jwtAuthFilter,
                          ExceptionHandlerFilter exceptionHandlerFilter,
                          UserDetailsService userDetailsService) {
        this.objectMapper = objectMapper;
        this.jwtAuthFilter = jwtAuthFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Exposes the {@link AuthenticationManager} bean from the Spring Security configuration.
     *
     * @param authenticationConfiguration the global authentication configuration
     * @return the configured authentication manager instance
     * @throws Exception if an error occurs while retrieving the manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the main security filter chain: disables CSRF, sets session policy to stateless,
     * registers authorization rules, handles 401/403 responses in JSON, and orders custom filters.
     *
     * @param http the {@link HttpSecurity} builder
     * @return the built {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTHORIZED_URLS).permitAll()
                .requestMatchers(HttpMethod.GET, AUTHORIZED_URLS_BY_METHOD.get(HttpMethod.GET)).permitAll()
                .requestMatchers(HttpMethod.POST, AUTHORIZED_URLS_BY_METHOD.get(HttpMethod.POST)).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, AUTHORIZED_URLS_BY_METHOD.get(HttpMethod.OPTIONS)).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");

                    Map<String, Object> res = new LinkedHashMap<>();
                    res.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                    res.put("error", "Unauthorized");
                    res.put("message", "Required authentication.");
                    res.put("path", request.getRequestURI());

                    response.getWriter().write(objectMapper.writeValueAsString(res));
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");

                    Map<String, Object> res = new LinkedHashMap<>();
                    res.put("status", HttpServletResponse.SC_FORBIDDEN);
                    res.put("error", "Forbidden");
                    res.put("message", "You do not have permission to access this resource");
                    res.put("path", request.getRequestURI());

                    response.getWriter().write(objectMapper.writeValueAsString(res));
                })
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtAuthFilter, ExceptionHandlerFilter.class)
            .userDetailsService(userDetailsService)
            .build();
    }

    /**
     * Configures the Cross-Origin Resource Sharing (CORS) policy for all endpoints.
     *
     * @return the registered {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(Duration.ofSeconds(EXPIRATION_TIME_SECONDS));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}