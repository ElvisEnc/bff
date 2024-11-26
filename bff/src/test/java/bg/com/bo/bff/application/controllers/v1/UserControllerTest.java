package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.user.UserRequestFixture;
import bg.com.bo.bff.application.dtos.request.user.UpdateDataUserRequest;
import bg.com.bo.bff.application.dtos.response.user.BiometricsResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.UserResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.apiface.DistrictsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.EconomicActivityResponse;
import bg.com.bo.bff.application.dtos.response.user.MaritalStatusResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateDataUserResponse;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private IUserService userService;
    private MockMvc mockMvc;
    @Mock
    private HttpServletRequest httpServletRequest;
    private HttpHeaders headers;
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_IP = "127.0.0.1";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";
    private static final String URL_CHANGE_PASSWORD = "/api/v1/users/999/change-password";
    private static final String URL_BIOMETRICS = "/api/v1/users/{personId}/biometric";
    private static final String URL_UPDATE_DATA_USER = "/api/v1/users/{personId}/info";
    private static final String JSON_DATA = "eyJkYXRvcyI6ImRzYWRhcyJ9";
    Enumeration<String> enumerations;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        Map<String, String> map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), DEVICE_ID,
                DeviceMW.DEVICE_IP.getCode(), DEVICE_IP,
                DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME,
                DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X,
                DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y,
                DeviceMW.APP_VERSION.getCode(), APP_VERSION
        );

        Vector<String> lists = new Vector<>(map.keySet().stream().toList());
        enumerations = lists.elements();
        headers.add(DeviceMW.DEVICE_ID.getCode(), DEVICE_ID);
        headers.add(DeviceMW.DEVICE_IP.getCode(), DEVICE_IP);
        headers.add(DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME);
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X);
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y);
        headers.add(DeviceMW.APP_VERSION.getCode(), APP_VERSION);

    }

    @Test
    void givenValidDataWhenChangePasswordThenReturnOk() throws Exception {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setNewPassword("1");
        request.setOldPassword("2");

        GenericResponse expected = new GenericResponse();

        when(userService.changePassword(any(), any(), any(), any())).thenReturn(expected);

        // Act & Assert
        mockMvc.perform(put(URL_CHANGE_PASSWORD)
                        .header("middleware-channel", "1")
                        .header("application-id", "2")
                        .header("device-id", "17177")
                        .header("device-ip", "127.0.1.1")
                        .header("device-name", "OS")
                        .header("geo-position-x", "10101.12")
                        .header("geo-position-y", "10101.12")
                        .header("app-version", "1.0.1")
                        .header("person-role-id", "10")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andReturn();

        // Arrange
        verify(userService).changePassword(any(), any(), any(), any());
    }

    @Test
    void givenInvalidDataWhenChangePasswordThenReturnBadRequest() throws Exception {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();

        // Act & Assert
        mockMvc.perform(put(URL_CHANGE_PASSWORD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void givenValidDataWhenGetContactInformation() throws Exception {
        // Arrange
        ContactResponse expected = UserResponseFixture.withDefaultContactResponse();
        when(userService.getContactInfo()).thenReturn(expected);

        // Act & Assert
        String url = "/api/v1/users/contact";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService).getContactInfo();
    }

    @Test
    void givenValidDataWhenGetPersonalInformation() throws Exception {
        // Arrange
        PersonalResponse expected = UserResponseFixture.withDefaultPersonalResponse();
        when(userService.getPersonalInformation(any(), any())).thenReturn(expected);

        // Act & Assert
        String url = "/api/v1/users/123/info";
        mockMvc.perform(get(url)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(userService).getPersonalInformation(any(), any());
    }

    @Test
    void givenValidPersonIdWhenGetBiometricStatus() throws Exception {
        // Assert
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        BiometricsResponse expected = UserResponseFixture.withDefaultBiometricsResponse();
        when(userService.getBiometrics(any(), any())).thenReturn(expected);

        // Act
        mockMvc.perform(get(URL_BIOMETRICS, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andReturn();

        // Arrange
        verify(userService).getBiometrics(any(), any());
    }

    @Test
    void givenPersonIdWhenUpdateBiometricThenResponseExpected() throws Exception {
        // Arrange
        HttpHeaders localHeaders = new HttpHeaders();
        localHeaders.add("json-data", JSON_DATA);
        localHeaders.addAll(headers);

        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        UpdateBiometricsResponse expected = UserResponseFixture.withDefaultUpdateBiometricsResponse();
        UpdateBiometricsRequest request = UserRequestFixture.withDefaultUpdateBiometricsRequest();
        when(userService.updateBiometrics(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(put(URL_BIOMETRICS, "123")
                        .headers(localHeaders)
                        .content(Util.objectToString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseExpected = Util.objectToString(expected);
        String response = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, responseExpected);
        verify(userService).updateBiometrics(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenGetEconomicActivityThenResponseExpected() throws Exception {
        // Assert
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        EconomicActivityResponse expected = UserResponseFixture.withDefaultEconomicActivityResponse();
        when(userService.getEconomicActivity(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/users/{personId}/economical-activity", "123")
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseExpected = Util.objectToString(expected);
        String response = result.getResponse().getContentAsString();

        // Arrange
        assertEquals(response, responseExpected);
        verify(userService).getEconomicActivity(any(), any());
    }

    @Test
    void givenValidDataWhenGetDepartments() throws Exception {
        // Arrange
        DepartmentsResponse expected = UserResponseFixture.withDefaultDepartmentsResponse();
        when(userService.getDepartments(any())).thenReturn(expected);

        // Act & Assert
        String url = "/api/v1/users/departments";
        mockMvc.perform(get(url)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Arrange
        verify(userService).getDepartments(any());
    }

    @Test
    void givenDepartmentIdWhenGetDistricts() throws Exception {
        // Arrange
        DistrictsResponse expected = UserResponseFixture.withDefaultDistrictsResponse();
        when(userService.getDistricts(any(), any())).thenReturn(expected);

        // Act & Assert
        String url = "/api/v1/users/departments/123/dictricts";
        MvcResult result = mockMvc.perform(get(url)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseExpected = Util.objectToString(expected);
        String response = result.getResponse().getContentAsString();

        // Arrange
        assertEquals(response, responseExpected);
        verify(userService).getDistricts(any(), any());
    }

    @Test
    void whenGetMaritalStatusThenResponseExpected() throws Exception {
        // Assert
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        MaritalStatusResponse expected = UserResponseFixture.withDefaultMaritalStatusResponse();
        when(userService.getMaritalStatus(any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get("/api/v1/users/marital-statuses")
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseExpected = Util.objectToString(expected);
        String response = result.getResponse().getContentAsString();

        // Arrange
        assertEquals(response, responseExpected);
        verify(userService).getMaritalStatus(any());
    }

    @Test
    void givenUpdateDataUserRequestWhenUpdateDataUserThenUpdateDataUserResponse() throws Exception {
        // Assert
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        GenericResponse expected = GenericResponse.instance(UpdateDataUserResponse.SUCCESS);
        UpdateDataUserRequest request = UserRequestFixture.withDefaultUpdateDataUserRequest();
        when(userService.updateDataUser(any(), any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(post(URL_UPDATE_DATA_USER, "123")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers)
                        .content(Util.objectToString(request)))

                .andExpect(status().isOk())
                .andReturn();

        String responseExpected = Util.objectToString(expected);
        String response = result.getResponse().getContentAsString();

        // Arrange
        assertEquals(response, responseExpected);
        verify(userService).updateDataUser(any(), any(), any());
    }
}
