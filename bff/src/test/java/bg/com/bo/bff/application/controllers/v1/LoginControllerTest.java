package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.login.*;
import bg.com.bo.bff.application.dtos.response.login.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.application.LoginMapper;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.providers.models.enums.middleware.GenericControllerResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @Spy
    @InjectMocks
    private LoginController loginController;
    @Mock
    private ILoginServices iLoginServices;
    @Mock
    DeviceEnrollmentResponse deviceEnrollmentResponse;
    private final LoginMapper loginMapper = LoginMapper.INSTANCE;
    LoginRequest requestMock = LoginRequestFixture.withDefaultLoginRequest();
    String personId = "123";
    String header = "123456";
    static Device device;
    @Mock
    static HttpServletRequest servletRequest;
    private MockMvc mockMvc;
    private final HttpHeaders headers = new HttpHeaders();
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_IP = "127.0.0.1";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";
    private static final String JSON_DATA = "{...}";
    Enumeration<String> enumerations;


    @BeforeEach
    public void init() {
        device = new Device();

        Map<String, String> map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), DEVICE_ID,
                DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME,
                DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X,
                DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y,
                DeviceMW.APP_VERSION.getCode(), APP_VERSION,
                "json-data", JSON_DATA
        );

        Vector<String> lists = new Vector<>(map.keySet().stream().toList());
        enumerations = lists.elements();

        headers.add(DeviceMW.DEVICE_ID.getCode(), DEVICE_ID);
        headers.add(DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME);
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X);
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y);
        headers.add(DeviceMW.APP_VERSION.getCode(), APP_VERSION);
        headers.add("json-data", JSON_DATA);

        loginController = new LoginController(this.iLoginServices, this.loginMapper, servletRequest);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void givenUserLoginWhenCredentialValueThenItReturnSuccessfully() throws Exception {
        // Arrange
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        TokenData tokenData = new TokenData();
        tokenData.setRefreshToken(refreshToken);
        tokenData.setAccessToken(accessToken);

        LoginResult resultExpected = new LoginResult();
        resultExpected.setPersonId(personId);
        resultExpected.setTokenData(tokenData);
        resultExpected.setStatusCode(LoginResult.StatusCode.SUCCESS);

        when(servletRequest.getHeaderNames()).thenReturn(enumerations);
        when(servletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        Mockito.when(iLoginServices.login(any(), any())).thenReturn(resultExpected);

        // Act
        ResponseEntity<LoginResponse> response = loginController.login(header, header, header, header, header, header, requestMock);
        System.out.println(response);
        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(accessToken, response.getBody().getAccessToken());
        assertEquals(refreshToken, response.getBody().getRefreshToken());
        assertEquals(personId, response.getBody().getUserData().getPersonId());
    }

    @Test
    void givenUserLoginWhenCredentialsAreInvalidThenReturnUserUnauthorized() throws Exception {
        // Arrange
        when(servletRequest.getHeaderNames()).thenReturn(enumerations);
        when(servletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        Mockito.when(iLoginServices.login(any(), any())).thenThrow(new GenericException(HttpStatus.UNAUTHORIZED.name()));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> loginController.login(header, header, header, header, header, header, requestMock));

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.name(), exception.getMessage());
    }

    @Test
    void givenExceptionWhenRequestLoginThenReturnsExceptionInternalServerError() throws Exception {
        // Arrange
        when(servletRequest.getHeaderNames()).thenReturn(enumerations);
        when(servletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        Mockito.when(iLoginServices.login(any(), any())).thenThrow(new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.name()));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginController.login(header, header, header, header, header, header, requestMock));

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage());
    }

    @Test
    void givenDeviceWhenRequestValidateEnrolledThenSuccessfully() throws IOException {
        // Arrange
        deviceEnrollmentResponse = new DeviceEnrollmentResponse();
        Mockito.when(iLoginServices.validation(any())).thenReturn(deviceEnrollmentResponse);

        // Act
        ResponseEntity<DeviceEnrollmentResponse> response = loginController.validateEnrollment("", "", "", "", "");

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
        GenericResponse expect = GenericResponse.instance(GenericControllerResponse.SUCCESS);

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
                        .header("json-data", generic)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        //Assert
        verify(iLoginServices).logout(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(LogoutRequest.class));
    }

    @Test
    void givenValidDataWhenRefreshThenReturnSuccess() throws Exception {
        // Arrange
        TokenDataResponse response = new TokenDataResponse();
        String accessJwt = UUID.randomUUID().toString();
        String refreshJwt = UUID.randomUUID().toString();
        RefreshSessionRequest refreshSessionRequest = new RefreshSessionRequest();
        refreshSessionRequest.setRefreshToken(refreshJwt);
        when(iLoginServices.refreshSession(any(), any(), any())).thenReturn(response);

        // Act
        mockMvc.perform(post("/api/v1/login/{personId}/refresh", 12345)
                        .header("Authorization", accessJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(refreshSessionRequest)))
                .andExpect(status().isOk());

        // Assert
        verify(iLoginServices).refreshSession(any(), any(), any());
    }
}
