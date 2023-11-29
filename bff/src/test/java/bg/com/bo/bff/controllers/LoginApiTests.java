package bg.com.bo.bff.controllers;

import bg.com.bo.bff.model.*;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class LoginApiTests {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private ILoginServices iLoginServices;

    static LoginRequest loginRequest;

    @BeforeAll
    public static void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setComplemento("a");
        loginRequest.setPassword("abc123");
        loginRequest.setCanal("GANAMOVIL");
    }

    @Test
    void givenUserLoginWhenCredentialValueThenItReturnSuccessfuly() throws IOException {
        // Arrange
        int codigoPersona = 10;
        loginRequest.setCedula("123456");
        User user = new User(10);
        LoginResponse loginResponse = new LoginResponse("SUCCESS", user);
        Mockito.when(iLoginServices.loginRequest(loginRequest)).thenReturn(loginResponse);

        // Act
        ResponseEntity<LoginResponse> response = (ResponseEntity<LoginResponse>) loginController.login(loginRequest);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        assert response.getBody().getData().getCodigoPersona() == codigoPersona;
    }

    @Test
    void givenUserLoginWhenCredentialsAreInvalidThenReturnUserUnauthorized() throws IOException {
        // Arrange
        loginRequest.setCedula("0000");
        Mockito.when(iLoginServices.loginRequest(loginRequest)).thenThrow(new UnauthorizedException(HttpStatus.UNAUTHORIZED.name()));

        // Act
        ResponseEntity<ErrorResponse> response = (ResponseEntity<ErrorResponse>) loginController.login(loginRequest);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value();
        assert response.getBody().getMessage().equals(HttpStatus.UNAUTHORIZED.name());
    }
}
