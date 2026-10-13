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

/**
 * Filter responsible for catching and handling unhandled exceptions thrown during
 * the servlet filter chain execution, particularly JWT processing errors.
 * Ensures consistent JSON error responses before reaching the Spring MVC dispatcher.
 */
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;
  private final Logger logger;

  /**
   * Constructs the exception handler filter with the specified JSON object mapper.
   *
   * @param objectMapper the mapper used to serialize error payloads into JSON format
   */
  public ExceptionHandlerFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    logger = LoggerFactory.getLogger(this.getClass());
  }

  /**
   * Intercepts the request and wraps subsequent filter chain execution in a try-catch block.
   * Formats and writes a structured JSON error response if an unhandled exception occurs.
   *
   * @param request     the current HTTP request
   * @param response    the current HTTP response
   * @param filterChain the remaining filter chain to execute
   * @throws IOException if an input or output error occurs while writing the response
   */
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