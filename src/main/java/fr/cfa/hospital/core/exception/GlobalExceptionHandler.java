package fr.cfa.hospital.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger;

    public GlobalExceptionHandler() {
        this.logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    }

    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFoundExceptions(Exception ex) {
        return getResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleForbiddenException(Exception ex) {
        return getResponseEntity(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
        UnsatisfiedServletRequestParameterException.class,
        ProvidedSaveIdException.class,
        FileEmptyException.class,
        NotProvidedUpdateIdException.class,
        NumberFormatException.class,
        DataIntegrityViolationException.class
    })
    protected ResponseEntity<Object> handleBadRequest(Exception ex) {
        return getResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    protected ResponseEntity<Object> handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        return getResponseEntity("The provided version does not match the current version", ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleGenericExceptions(Exception ex) {
        return getResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

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

            String simpleClassName = originClass.substring(originClass.lastIndexOf('.') + 1);

            body.put("origin", simpleClassName + "." + originMethod);
        }

        logger.error("Error {} thrown in {} (method {}) - Message: {}",
                status.value(), originClass, originMethod, ex.getMessage(), ex);

        return new ResponseEntity<>(body, status);
    }
}
