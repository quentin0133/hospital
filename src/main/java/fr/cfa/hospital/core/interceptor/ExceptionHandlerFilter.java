package fr.cfa.hospital.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws IOException {
    try {
      filterChain.doFilter(request, response);
    }
    catch (Exception ex) {
      log.error("Une erreur s'est produite dans les filtres : {}", ex.getMessage());

      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      String message = "Une erreur inattendue est survenue";

      if (ex instanceof JwtException || ex.getCause() instanceof JwtException) {
        status = HttpServletResponse.SC_UNAUTHORIZED;
        message = ex.getMessage();

        if(ex instanceof ExpiredJwtException) {
          message = "Le token JWT est expiré. Veuillez vous reconnecter.";
        }
      }

      Map<String, Object> res = new LinkedHashMap<>();
      res.put("status", status);
      res.put("error", HttpStatus.valueOf(status).getReasonPhrase());
      res.put("message", message);
      res.put("path", request.getRequestURI());

      String jsonString = objectMapper.writeValueAsString(res);

      response.setContentType("application/json");
      response.setStatus(status);
      response.getWriter().write(jsonString);
    }
  }
}
