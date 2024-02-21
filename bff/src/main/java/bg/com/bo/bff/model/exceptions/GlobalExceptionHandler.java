package bg.com.bo.bff.model.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;

import bg.com.bo.bff.config.exception.GenericException;
import bg.com.bo.bff.controllers.LoginController;
import bg.com.bo.bff.model.ErrorResponse;
import bg.com.bo.bff.model.enums.HttpError;
import bg.com.bo.bff.model.util.Util;
import bg.com.bo.bff.provider.response.ErrorDetailResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.mapper.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.asm.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(LoginController.class.getName());

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.Error401.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleNotAcceptableException(NotAcceptableException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.Error406.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.Error400.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleNotBlankParamsException(ConstraintViolationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.Error422.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpError.Error500.getName(), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> genericException(GenericException exception) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(exception.getStatus().value()), exception.getMessage());
        logger.error(exception);
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

}
