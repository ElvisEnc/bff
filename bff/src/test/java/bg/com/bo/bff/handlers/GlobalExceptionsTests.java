package bg.com.bo.bff.handlers;

import bg.com.bo.bff.model.ErrorResponse;
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

public class GlobalExceptionsTests {
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
        assert response.getStatusCode().equals(HttpStatus.UNAUTHORIZED);
        assert response.getBody().getMessage().equals(HttpStatus.UNAUTHORIZED.name());
    }

    @Test
    public void givenExcepionWhenLoginThenErrorResponseInternalServerError() {
        // Arrange
        Exception exception = new Exception(HttpStatus.INTERNAL_SERVER_ERROR.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(exception);

        // Assert
        assert response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenNotAcceptableExceptionWhenRequestThenErrorResponseNotAcceptable(){
        // Arrange
        NotAcceptableException exception = new NotAcceptableException(HttpStatus.NOT_ACCEPTABLE.name());

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleNotAcceptableException(exception);

        // Assert
        assert response.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE);
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
