package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.login.*;
import bg.com.bo.bff.application.dtos.response.login.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.enums.middleware.GenericControllerResponse;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @Spy
    @InjectMocks
    private LoginController loginController;
    @Mock
    private ILoginServices iLoginServices;

    LoginRequest requestMock = LoginRequestFixture.withDefaultLoginRequest();
    String header = "123456";
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        loginController.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenUserLoginWhenCredentialValueThenItReturnSuccessfully() throws Exception {
        // Arrange
        LoginResponse resultExpected = LoginResponseFixture.withDefaultLoginResponse();
        when(iLoginServices.login(any())).thenReturn(resultExpected);

        // Act
        ResponseEntity<LoginResponse> response = loginController.login(header, requestMock);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(resultExpected.getAccessToken(), response.getBody().getAccessToken());
        assertEquals(resultExpected.getRefreshToken(), response.getBody().getRefreshToken());
        assertEquals(resultExpected.getUserData().getPersonId(), response.getBody().getUserData().getPersonId());
    }

    @Test
    void givenUserLoginWhenCredentialsAreInvalidThenReturnUserUnauthorized() throws Exception {
        // Arrange
        when(iLoginServices.login(any())).thenThrow(new GenericException(HttpStatus.UNAUTHORIZED.name()));

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> loginController.login(header, requestMock));

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.name(), exception.getMessage());
    }

    @Test
    void givenExceptionWhenRequestLoginThenReturnsExceptionInternalServerError() throws Exception {
        // Arrange
        when(iLoginServices.login(any())).thenThrow(new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.name()));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginController.login(header, requestMock));

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage());
    }

    @Test
    void givenDeviceWhenRequestValidateEnrolledThenSuccessfully() throws IOException {
        // Arrange
        DeviceEnrollmentResponse mockResponse = LoginResponseFixture.withDefaultDeviceEnrollmentResponse();
        when(iLoginServices.validation()).thenReturn(mockResponse);

        // Act
        ResponseEntity<DeviceEnrollmentResponse> response = loginController.validateEnrollment();

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        assertNotNull(response.getBody());
    }

    @Test
    void whenLogoutThenSuccess() throws Exception {
        // Arrange
        String generic = "546545432";
        LogoutRequest request = new LogoutRequest();
        request.setRefreshToken("refreshTokenValue");
        GenericResponse expect = GenericResponse.instance(GenericControllerResponse.SUCCESS);

        when(iLoginServices.logout(any(), any(), any(), any())).thenReturn(expect);

        //Act
        mockMvc.perform(post("/api/v1/login/{personId}/logout", 12345)
                        .header("Authorization", generic)
                        .header("user-device-id", generic)
                        .header("person-role-id", generic)
                        .header("json-data", generic)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        //Assert
        verify(iLoginServices).logout(any(), any(), any(), any(LogoutRequest.class));
    }

    @Test
    void givenValidDataWhenRefreshThenReturnSuccess() throws Exception {
        // Arrange
        TokenDataResponse mockResponse = LoginResponseFixture.withDefaultTokenDataResponse();
        String accessJwt = UUID.randomUUID().toString();
        String refreshJwt = UUID.randomUUID().toString();
        RefreshSessionRequest refreshSessionRequest = new RefreshSessionRequest();
        refreshSessionRequest.setRefreshToken(refreshJwt);
        when(iLoginServices.refreshSession(any(), any(), any())).thenReturn(mockResponse);

        // Act
        String path = "/api/v1/login/{personId}/refresh";
        MvcResult result = mockMvc.perform(post(path, "123456")
                        .header("Authorization", accessJwt)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(refreshSessionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        String response = result.getResponse().getContentAsString();
        String expectedJsonResponse = Util.objectToString(mockResponse);

        assertNotNull(result);
        assertEquals(expectedJsonResponse, response);
        verify(iLoginServices).refreshSession(any(), any(), any());
    }
}
