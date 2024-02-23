package bg.com.bo.bff.handlers;

import bg.com.bo.bff.model.dtos.ErrorResponse;
import bg.com.bo.bff.model.exceptions.BadRequestException;
import bg.com.bo.bff.model.exceptions.GlobalExceptionHandler;
import bg.com.bo.bff.model.exceptions.NotAcceptableException;
import bg.com.bo.bff.model.exceptions.UnauthorizedException;
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
    public void givenUnauthorizedExceptionWhenLoginRequestThenErrorResponseUnauthorizedException() {
        // Arrange
        UnauthorizedException unauthorizedException = new UnauthorizedException(HttpStatus.UNAUTHORIZED.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUnauthorizedException(unauthorizedException);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED.name(), response.getBody().getMessage());
    }

    @Test
    public void givenExcepionWhenLoginThenErrorResponseInternalServerError() {
        // Arrange
        Exception exception = new Exception(HttpStatus.INTERNAL_SERVER_ERROR.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void givenNotAcceptableExceptionWhenRequestThenErrorResponseNotAcceptable() {
        // Arrange
        NotAcceptableException exception = new NotAcceptableException(HttpStatus.NOT_ACCEPTABLE.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotAcceptableException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void givenBadRequestExceptionWhenRequestThenErrorResponseBadRequest(){
        // Arrange
        BadRequestException exception = new BadRequestException(HttpStatus.BAD_REQUEST.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBadRequestException(exception);

        // Assert
        assert response.getStatusCode().equals(HttpStatus.BAD_REQUEST);
    }
}
