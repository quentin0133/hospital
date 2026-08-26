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

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretString;

    @Value("${jwt.token.duration}")
    private long tokenDuration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    public Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
            .verifyWith(this.secretKey)
            .build()
            .parseSignedClaims(jwtToken)
            .getPayload();
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails, new Date(System.currentTimeMillis() + tokenDuration * 1000));
    }

    private String createToken(UserDetails userDetails, Date expiration) {
        return Jwts.builder()
            .claims(getClaims(userDetails))
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expiration)
            .signWith(this.secretKey)
            .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Map<String, Object> getClaims(UserDetails userDetails) {
        if (userDetails == null)
            throw new UsernameNotFoundException("User not found");
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return claims;
    }
}