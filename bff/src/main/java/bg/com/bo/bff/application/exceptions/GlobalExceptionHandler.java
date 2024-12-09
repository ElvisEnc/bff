package bg.com.bo.bff.application.exceptions;


import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler({MissingRequestHeaderException.class, MethodArgumentTypeMismatchException.class, ConstraintViolationException.class, UnexpectedTypeException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), exception.getMessage(), "Bad request", CategoryError.INVALID_FORMAT.getCategoryId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        String message = String.format("%s: El campo es requerido.", exception.getParameterName());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), message, "Bad request", CategoryError.INVALID_FORMAT.getCategoryId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String specificMessage = extractSpecificMessage(ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), specificMessage, "Bad request", CategoryError.INVALID_FORMAT.getCategoryId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private String extractSpecificMessage(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ife) {
            String fieldName = ife.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));
            String targetType = ife.getTargetType().getSimpleName();
            return String.format("El campo '%s' debe ser un %s válido", fieldName, targetType);
        }
        return "El formato no es el esperado";
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationMessageException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        List<String> orderedErrors = errors.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), String.join(", ", orderedErrors), "Bad request", CategoryError.INVALID_FORMAT.getCategoryId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        List<String> errors = new ArrayList<>();
        for (MessageSourceResolvable error : ex.getAllErrors())
            if (error instanceof FieldError)
                errors.add(((FieldError) error).getField() + ": " + error.getDefaultMessage());
            else
                errors.add((error.getDefaultMessage()));

        List<String> orderedErrors = errors.stream().sorted(Comparator.nullsFirst(Comparator.naturalOrder())).toList();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), String.join(", ", orderedErrors), "Bad request", CategoryError.INVALID_FORMAT.getCategoryId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorResponse errorResponse = ErrorResponse.instance(DefaultMiddlewareError.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error(exception);
        ErrorResponse errorResponse = ErrorResponse.instance(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> genericException(GenericException exception) {
        ErrorResponse errorResponse = ErrorResponse.instance(exception);
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(HandledException.class)
    public ResponseEntity<ErrorResponse> handledException(HandledException exception) {
        ErrorResponse errorResponse = ErrorResponse.instance(exception);
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }
}