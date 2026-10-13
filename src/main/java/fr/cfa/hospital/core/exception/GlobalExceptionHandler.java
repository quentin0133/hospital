package fr.cfa.hospital.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for API controllers.
 * Centralizes HTTP error response formatting and logging.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger;

    /**
     * Initializes the logger dedicated to exception handling.
     */
    public GlobalExceptionHandler() {
        this.logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    }

    /**
     * Handles exceptions triggered when a requested resource or route is not found.
     *
     * @param ex the intercepted exception
     * @return a structured HTTP 404 (NOT_FOUND) response entity
     */
    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFoundExceptions(Exception ex) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles authentication failures (missing, expired, or invalid credentials/token).
     *
     * @param ex the authentication exception
     * @return an HTTP 401 (UNAUTHORIZED) response entity
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(Exception ex) {
        return getResponseEntity(ex, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles access denial when an authenticated user lacks required permissions.
     *
     * @param ex the access denied exception
     * @return an HTTP 403 (FORBIDDEN) response entity
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleForbiddenException(Exception ex) {
        return getResponseEntity(ex, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles request validation issues, missing required parameters, or data integrity violations.
     *
     * @param ex the intercepted client request exception
     * @return an HTTP 400 (BAD_REQUEST) response entity
     */
    @ExceptionHandler({
        UnsatisfiedServletRequestParameterException.class,
        ProvidedSaveIdException.class,
        FileEmptyException.class,
        NotProvidedUpdateIdException.class,
        NumberFormatException.class,
        DataIntegrityViolationException.class,
        AccountExistsException.class
    })
    protected ResponseEntity<Object> handleBadRequest(Exception ex) {
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles concurrent modification conflicts caused by mismatched entity versioning.
     *
     * @param ex the optimistic locking failure exception
     * @return an HTTP 409 (CONFLICT) response entity with a descriptive error message
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    protected ResponseEntity<Object> handleObjectOptimisticLockingFailureException(OptimisticLockingFailureException ex) {
        return getResponseEntity("The provided version does not match the current version", ex, HttpStatus.CONFLICT);
    }

    /**
     * Fallback handler for unhandled generic exceptions to prevent leaking raw internal stack traces.
     *
     * @param ex the unexpected exception
     * @return an HTTP 500 (INTERNAL_SERVER_ERROR) response entity
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleGenericExceptions(Exception ex) {
        return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds the error response body using a custom message, extracts call origin information,
     * and logs the exception details.
     *
     * @param message the custom error message to return to the client
     * @param ex the intercepted exception
     * @param status the target HTTP status
     * @return a formatted {@link ResponseEntity} containing error details
     */
    private ResponseEntity<Object> getResponseEntity(String message, Exception ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errors", status.value());
        body.put("message", message);

        String originClass = "Unknown";
        String originMethod = "Unknown";

        if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
            StackTraceElement firstElement = ex.getStackTrace()[0];

            originClass = firstElement.getClassName();
            originMethod = firstElement.getMethodName();

            String simpleClassName = originClass.substring(originClass.lastIndexOf('.') + 1);

            body.put("origin", simpleClassName + "." + originMethod);
        }

        logger.error("Error {} thrown in {} (method {}) - Message: {}",
            status.value(), originClass, originMethod, ex.getMessage(), ex);

        return new ResponseEntity<>(body, status);
    }

    /**
     * Builds the error response body using the original exception message, extracts call origin information,
     * and logs the exception details.
     *
     * @param ex the intercepted exception
     * @param status the target HTTP status
     * @return a formatted {@link ResponseEntity} containing error details
     */
    private ResponseEntity<Object> getResponseEntity(Exception ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errors", status.value());
        body.put("message", ex.getMessage());

        String originClass = "Unknown";
        String originMethod = "Unknown";

        if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
            StackTraceElement firstElement = ex.getStackTrace()[0];

            originClass = firstElement.getClassName();
            originMethod = firstElement.getMethodName();
        }

        logger.error("Error {} thrown in {} (method {}) - Message: {}",
            status.value(), originClass, originMethod, ex.getMessage(), ex);

        return new ResponseEntity<>(body, status);
    }
}