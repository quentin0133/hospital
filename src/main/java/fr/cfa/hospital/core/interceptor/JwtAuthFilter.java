package fr.cfa.hospital.core.interceptor;

import fr.cfa.hospital.core.tools.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter responsible for intercepting HTTP requests to validate JWT tokens.
 * Extracts the Bearer token from the Authorization header, validates its authenticity,
 * loads user details, and populates the Spring SecurityContext.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    /**
     * Constructs the JWT authentication filter with required dependencies.
     *
     * @param userDetailsService service used to load user accounts during token validation
     * @param jwtUtils           utility class for parsing and verifying JWT tokens
     */
    public JwtAuthFilter(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Inspects the Authorization header of incoming requests.
     * If a valid Bearer token is found and no authentication exists in the current SecurityContext,
     * it validates the token and sets the authenticated principal.
     *
     * @param request     the incoming HTTP request
     * @param response    the outgoing HTTP response
     * @param filterChain the remaining servlet filter chain
     * @throws ServletException if an error occurs during filter processing
     * @throws IOException      if an I/O error occurs during request handling
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.toLowerCase().startsWith("bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        String username = jwtUtils.extractUsername(jwtToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}