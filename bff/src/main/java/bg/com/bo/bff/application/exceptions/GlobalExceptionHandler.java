package bg.com.bo.bff.application.exceptions;


import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.commons.enums.HttpError;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler({MissingRequestHeaderException.class, MethodArgumentTypeMismatchException.class, ConstraintViolationException.class, UnexpectedTypeException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
        logger.debug(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String specificMessage = extractSpecificMessage(ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), specificMessage);
        logger.debug(ex);
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationMessageException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), String.join(", ", errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.ERROR_401.getName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.name());
        logger.debug(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleNotAcceptableException(NotAcceptableException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.ERROR_406.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.ERROR_400.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({Exception.class, NotHandledResponseException.class, CreateTokenServiceException.class, RequestException.class, JwtServiceException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.ERROR_500.getName(), "Hubo un error no controlado.");
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> genericException(GenericException exception) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(exception.getCode()), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(HandledException.class)
    public ResponseEntity<ErrorResponse> handledException(HandledException exception) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(exception.getCode()), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }
}
