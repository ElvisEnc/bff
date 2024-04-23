package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.Device;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.LogoutRequest;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.LoginResponse;
import bg.com.bo.bff.application.mappings.login.LoginMapper;
import bg.com.bo.bff.models.dtos.login.*;
import bg.com.bo.bff.application.exceptions.UnauthorizedException;
import bg.com.bo.bff.services.interfaces.IDeviceEnrollmentService;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private LoginController loginController;

    @Mock
    private ILoginServices iLoginServices;

    @Mock
    private IDeviceEnrollmentService iDeviceEnrollmentService;

    private final LoginMapper loginMapper = LoginMapper.INSTANCE;

    static LoginRequest loginRequest;
    static Device device;

    @Mock
    DeviceEnrollmentResponse deviceEnrollmentResponse;
    static String ip;
    @Mock
    static HttpServletRequest servletRequest;

    private MockMvc mockMvc;


    @BeforeAll
    public static void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setComplement("a");
        loginRequest.setPassword("abc123");
        ip = "123.123.123.123";
        device = new Device();

    }

    @BeforeEach
    public void init() {
        loginController = new LoginController(this.iLoginServices, this.iDeviceEnrollmentService, this.loginMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
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

        Mockito.when(iLoginServices.login(loginRequest, ip)).thenReturn(loginResponse);
        Mockito.when(servletRequest.getRemoteAddr()).thenReturn(ip);

        // Act
        ResponseEntity<LoginResponse> response = loginController.login(loginRequest, servletRequest);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(accessToken, response.getBody().getAccessToken());
        assertEquals(refreshToken, response.getBody().getRefreshToken());
        assertEquals(personId, response.getBody().getUserData().getPersonId());
    }

    @Test
    void givenUserLoginWhenCredentialsAreInvalidThenReturnUserUnauthorized() throws Exception {
        // Arrange
        Mockito.when(iLoginServices.login(loginRequest, ip)).thenThrow(new UnauthorizedException(HttpStatus.UNAUTHORIZED.name()));
        Mockito.when(servletRequest.getRemoteAddr()).thenReturn(ip);

        // Act
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> loginController.login(loginRequest, servletRequest));

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.name(), exception.getMessage());
    }

    @Test
    void givenExceptionWhenRequestLoginThenReturnsExceptionInternalServerError() throws Exception {
        // Arrange
        Mockito.when(iLoginServices.login(loginRequest, ip)).thenThrow(new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.name()));
        Mockito.when(servletRequest.getRemoteAddr()).thenReturn(ip);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginController.login(loginRequest, servletRequest));

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage());
    }

    @Test
    void fivenDeviceWhenRequestValidateEnrolledThenSuccessfully() throws IOException {
        // Arrange
        deviceEnrollmentResponse = new DeviceEnrollmentResponse();
        Mockito.when(iDeviceEnrollmentService.validation(device)).thenReturn(deviceEnrollmentResponse);

        // Act
        ResponseEntity<DeviceEnrollmentResponse> response = loginController.validateEnrollment(device);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void whenLogoutThenSuccess() throws Exception {
        // Arrange
        String generic = "546545432";
        LogoutRequest request = new LogoutRequest();
        request.setRefreshToken("refreshTokenValue");
        GenericResponse expect = new GenericResponse("SUCCESS", "SUCCESS");

        Mockito.when(iLoginServices.logout(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(expect);

        //Act
        mockMvc.perform(post("/api/v1/login/{personId}/logout", 12345)
                        .header("Authorization", generic)
                        .header("device-id", generic)
                        .header("device-name", generic)
                        .header("geo-position-x", generic)
                        .header("geo-position-y", generic)
                        .header("app-version", generic)
                        .header("user-device-id", generic)
                        .header("person-role-id", generic)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        //Assert
        Mockito.verify(iLoginServices).logout(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(LogoutRequest.class));
    }
}
