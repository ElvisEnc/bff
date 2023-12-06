package bg.com.bo.service.template.handlers;

import bg.com.bo.service.template.model.ErrorResponse;
import bg.com.bo.service.template.model.exceptions.ExceptionNotFound;
import bg.com.bo.service.template.model.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionsTests {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenExceptionNotFoundWhenLoginRequestThenErrorResponseExceptionNotFound() {
        // Arrange
        ExceptionNotFound exceptionNotFound = new ExceptionNotFound(HttpStatus.NOT_FOUND.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExceptionNotFound(exceptionNotFound);

        // Assert
        assert response.getStatusCode().equals(HttpStatus.NOT_FOUND);
        assert response.getBody().getMessage().equals(HttpStatus.NOT_FOUND.name());
    }

    @Test
    void givenExcepionWhenLoginThenErrorResponseInternalServerError() {
        // Arrange
        Exception exception = new Exception(HttpStatus.INTERNAL_SERVER_ERROR.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(exception);

        // Assert
        assert response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR);
        assert response.getBody().getMessage().equals(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}
