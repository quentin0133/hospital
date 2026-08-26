package fr.cfa.hospital.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;
  private final Logger logger;

  public ExceptionHandlerFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    logger = LoggerFactory.getLogger(this.getClass());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws IOException {
    try {
      filterChain.doFilter(request, response);
    }
    catch (Exception ex) {
      logger.error("a error occured in the filters : {}", ex.getMessage());

      int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      String message = "A unknown error occured";

      if (ex instanceof JwtException || ex.getCause() instanceof JwtException) {
        status = HttpServletResponse.SC_UNAUTHORIZED;
        message = ex.getMessage();

        if(ex instanceof ExpiredJwtException) {
          message = "The token is expired, please log in again.";
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
