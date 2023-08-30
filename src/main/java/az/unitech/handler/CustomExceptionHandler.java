package az.unitech.handler;

import az.unitech.exception.IndividualChargerNotFoundException;
import az.unitech.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MISSING_PARAMETER = "Missing response body parameter!";

    public static final String VALIDATION_ERROR = "Validation error!";

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatusCode status,
                                                                         WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(getError(status, ex.getMessage(), ex.getMessage(), request),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        String errorDetail = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        logError(ex);
        return new ResponseEntity<>(getError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errorDetail, request),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(
                getError(HttpStatus.BAD_REQUEST, MISSING_PARAMETER,
                        ex.getMostSpecificCause().getMessage(), request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                        WebRequest request) {
        logError(ex);
        var errorDetail = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(getError(HttpStatus.BAD_REQUEST, errorDetail, errorDetail, request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handlerFailedRequest(ValidationException ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(getError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage(), request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IndividualChargerNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(IndividualChargerNotFoundException ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(getError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getMessage(), request),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCommonException(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(getError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage(), request),
                HttpStatus.BAD_REQUEST);
    }

    private ApiErrorDto getError(HttpStatusCode status, String message, String errorDetail, WebRequest request) {
        return new ApiErrorDto(LocalDateTime.now(),
                status.value(),
                message,
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                errorDetail);
    }


    private void logError(Exception e) {
        log.error("An exception {} occurred with message: {}, {}", e.getClass(), e.getMessage(), e.getCause());
    }
}
