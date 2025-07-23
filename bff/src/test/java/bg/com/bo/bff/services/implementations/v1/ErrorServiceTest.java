package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.implementations.v1.fixtures.ErrorServiceTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorServiceTest {
    private ErrorService service;
    private ErrorAttributes errorAttributes;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        service = new ErrorService();
        webRequest = mock(WebRequest.class);
        errorAttributes = mock(ErrorAttributes.class);
    }

    @Test
    void givenForbiddenResultWhenMapThenReturnForbiddenResponse() throws IOException {
        //Arrange
        when(errorAttributes.getErrorAttributes(any(), any())).thenReturn(ErrorServiceTestFixture.withForbidden());
        IMiddlewareError error = DefaultMiddlewareError.FORBIDDEN;
        ErrorResponse expected = ErrorResponse.instance(error);

        //Act
        ResponseEntity<ErrorResponse> response = service.map(errorAttributes, webRequest);

        //Assert
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expected);
        assertEquals(response.getStatusCode(), error.getHttpCode());
    }

    @Test
    void givenBadRequestResultWhenMapThenReturnBadRequestResponse() throws IOException {
        //Arrange
        when(errorAttributes.getErrorAttributes(any(), any())).thenReturn(ErrorServiceTestFixture.withBadRequest());
        IMiddlewareError error = DefaultMiddlewareError.BAD_REQUEST;
        ErrorResponse expected = ErrorResponse.instance(error);

        //Act
        ResponseEntity<ErrorResponse> response = service.map(errorAttributes, webRequest);

        //Assert
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expected);
        assertEquals(response.getStatusCode(), error.getHttpCode());
    }

    @Test
    void givenNoHandledResultWhenMapThenReturnInternalServerResponse() throws IOException {
        //Arrange
        when(errorAttributes.getErrorAttributes(any(), any())).thenReturn(ErrorServiceTestFixture.withUnauthorized());
        IMiddlewareError error = DefaultMiddlewareError.INTERNAL_SERVER_ERROR;
        ErrorResponse expected = ErrorResponse.instance(error);

        //Act
        ResponseEntity<ErrorResponse> response = service.map(errorAttributes, webRequest);

        //Assert
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expected);
        assertEquals(response.getStatusCode(), error.getHttpCode());
    }
}