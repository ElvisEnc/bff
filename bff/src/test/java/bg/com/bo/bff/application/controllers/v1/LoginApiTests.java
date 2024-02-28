package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.response.LoginResponse;
import bg.com.bo.bff.application.mappings.login.LoginMapper;
import bg.com.bo.bff.models.dtos.login.*;
import bg.com.bo.bff.application.exceptions.UnauthorizedException;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LoginApiTests {

    private LoginController loginController;

    @Mock
    private ILoginServices iLoginServices;

    private final LoginMapper loginMapper = LoginMapper.INSTANCE;

    static LoginRequest loginRequest;

    @BeforeAll
    public static void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setComplemento("a");
        loginRequest.setPassword("abc123");
    }

    @BeforeEach
    public void init() {
        loginController = new LoginController(this.iLoginServices, this.loginMapper);
    }

    @Test
    void givenUserLoginWhenCredentialValueThenItReturnSuccessfully() throws Exception {
        // Arrange
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        String personId = "1";

        TokenData tokenData = new TokenData();
        tokenData.setRefreshToken(refreshToken);
        tokenData.setAccessToken(accessToken);

        LoginResult loginResponse = new LoginResult();
        loginResponse.setPersonId(personId);
        loginResponse.setTokenData(tokenData);
        loginResponse.setStatusCode(LoginResult.StatusCode.SUCCESS);

        Mockito.when(iLoginServices.login(loginRequest)).thenReturn(loginResponse);

        // Act
        ResponseEntity<LoginResponse> response = loginController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(accessToken, response.getBody().getAccessToken());
        assertEquals(refreshToken, response.getBody().getRefreshToken());
        assertEquals(personId, response.getBody().getPersonId());
    }

    @Test
    void givenUserLoginWhenCredentialsAreInvalidThenReturnUserUnauthorized() throws Exception {
        // Arrange
        Mockito.when(iLoginServices.login(loginRequest)).thenThrow(new UnauthorizedException(HttpStatus.UNAUTHORIZED.name()));

        // Act
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> loginController.login(loginRequest));

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.name(), exception.getMessage());
    }

    @Test
    void givenExceptionWhenRequestLoginThenReturnsExceptionInternalServerError() throws Exception {
        // Arrange
        Mockito.when(iLoginServices.login(loginRequest)).thenThrow(new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.name()));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginController.login(loginRequest));

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage());
    }
}
