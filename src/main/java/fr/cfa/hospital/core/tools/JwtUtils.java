package fr.cfa.hospital.core.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility component handling JSON Web Token (JWT) lifecycle operations.
 * Provides capabilities for generating, signing, parsing, and validating JWT tokens.
 */
@Component
public class JwtUtils {

    /**
     * Secret key string injected from application properties used for HMAC signing.
     */
    @Value("${jwt.secret.key}")
    private String secretString;

    /**
     * Token validity duration in seconds injected from application properties.
     */
    @Value("${jwt.token.duration}")
    private long tokenDuration;

    private SecretKey secretKey;

    /**
     * Initializes the cryptographic HMAC SHA key once the bean properties are injected.
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the subject (username) from the given JWT token.
     *
     * @param jwtToken the encoded JWT token string
     * @return the extracted username
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Validates whether the token belongs to the given user and has not expired.
     *
     * @param jwtToken    the encoded JWT token string
     * @param userDetails the authenticated principal details to match against
     * @return {@code true} if the token matches the username and is not expired; {@code false} otherwise
     */
    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    /**
     * Checks if the given token has passed its expiration timestamp.
     *
     * @param jwtToken the encoded JWT token string
     * @return {@code true} if the current date is after the token expiration; {@code false} otherwise
     */
    public boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * Extracts the expiration date timestamp from the token claims.
     *
     * @param jwtToken the encoded JWT token string
     * @return the expiration {@link Date}
     */
    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Resolves a specific claim from the token payload using a resolver function.
     *
     * @param jwtToken       the encoded JWT token string
     * @param claimsResolver functional resolver applied to the parsed {@link Claims}
     * @param <T>            the expected return type of the claim
     * @return the resolved claim value
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Verifies the cryptographic signature and retrieves all claims embedded in the token.
     *
     * @param jwtToken the encoded JWT token string
     * @return the parsed {@link Claims} payload
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
            .verifyWith(this.secretKey)
            .build()
            .parseSignedClaims(jwtToken)
            .getPayload();
    }

    /**
     * Generates a signed JWT token containing user details, authorities, and expiration.
     *
     * @param userDetails the authenticated user details
     * @return the compact, URL-safe signed JWT string
     */
    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails, new Date(System.currentTimeMillis() + tokenDuration * 1000));
    }

    /**
     * Builds and signs a compact JWT token with subject, custom claims, and expiration.
     *
     * @param userDetails the authenticated user details
     * @param expiration  the token expiration date
     * @return the serialized JWT string
     */
    private String createToken(UserDetails userDetails, Date expiration) {
        return Jwts.builder()
            .claims(getClaims(userDetails))
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expiration)
            .signWith(this.secretKey)
            .compact();
    }

    /**
     * Extracts the raw Bearer token from the HTTP Authorization request header.
     *
     * @param request the incoming HTTP servlet request
     * @return the extracted JWT token, or {@code null} if the header is absent or does not start with Bearer
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Prepares custom claims to embed in the token, including user granted authorities.
     *
     * @param userDetails the user details instance
     * @return a map of custom claim key-value pairs
     * @throws UsernameNotFoundException if {@code userDetails} is null
     */
    private Map<String, Object> getClaims(UserDetails userDetails) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return claims;
    }
}