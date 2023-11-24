package bg.com.bo.bff.controllers;

import bg.com.bo.bff.model.ErrorResponse;
import bg.com.bo.bff.model.LoginRequest;
import bg.com.bo.bff.model.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class LoginApiTests {

    @Test
    void givenUserLoginWhenCredentialValueThenItReturnSuccessfuly() {
        // Arrange
        int codigoPersona = 10;
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCedula("123456");
        loginRequest.setComplemento("a");
        loginRequest.setPassword("abc123");

        // Act
        LoginController loginController = new LoginController();
        ResponseEntity<LoginResponse> response = (ResponseEntity<LoginResponse>) loginController.login(loginRequest);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        assert response.getBody().getData().getCodigoPersona() == codigoPersona;
    }

    @Test
    void givenUserLoginWhenCredentialsAreInvalidThenReturnUserUnauthorized() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCedula("0000");
        loginRequest.setComplemento("a1");
        loginRequest.setPassword("123asd");

        // Act
        LoginController loginController = new LoginController();
        ResponseEntity<ErrorResponse> response = (ResponseEntity<ErrorResponse>) loginController.login(loginRequest);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value();
        assert response.getBody().getMessage().equals(HttpStatus.UNAUTHORIZED.name());
    }
}
