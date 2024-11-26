package bg.com.bo.bff.application.handlers;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.exceptions.*;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionsTests {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenUnauthorizedExceptionWhenLoginRequestThenErrorResponseUnauthorizedException() {
        // Arrange
        GenericException exception = new GenericException(DefaultMiddlewareError.NOT_AUTHENTICATED_USER);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.genericException(exception);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void givenExcepionWhenLoginThenErrorResponseInternalServerError() {
        // Arrange
        GenericException exception = new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.genericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void givenNotAcceptableExceptionWhenRequestThenErrorResponseNotAcceptable() {
        // Arrange
        GenericException exception = new GenericException(DefaultMiddlewareError.NOT_ACCEPTABLE);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.genericException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    void givenBadRequestExceptionWhenRequestThenErrorResponseBadRequest(){
        // Arrange
        GenericException exception = new GenericException(DefaultMiddlewareError.BAD_REQUEST);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.genericException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
