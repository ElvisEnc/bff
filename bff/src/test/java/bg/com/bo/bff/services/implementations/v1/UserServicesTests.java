package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequestFixture;
import bg.com.bo.bff.application.dtos.response.*;
import bg.com.bo.bff.application.dtos.response.apiface.DepartmentsResponse;
import bg.com.bo.bff.application.dtos.response.user.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponse;
import bg.com.bo.bff.providers.dtos.response.apiface.DepartmentsNetResponseFixture;
import bg.com.bo.bff.providers.dtos.response.login.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.BiometricStatusMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IApiFaceNetProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.providers.mappings.apiface.IApiFaceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServicesTests {
    @Mock
    private ILoginMiddlewareProvider provider;
    @Mock
    private IPersonalInformationNetProvider personalInformationNetProvider;
    @Mock
    private IApiFaceNetProvider apiFaceNetProvider;
    @Mock
    private IApiFaceMapper iApiFaceMapper;
    @InjectMocks
    private UserService service;
    private Map<String, String> map;

    @BeforeEach
    void setUp() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        this.service = new UserService(provider, personalInformationNetProvider, apiFaceNetProvider, iApiFaceMapper);
    }

    @Test
    void givenValidDataWhenChangePasswordThenReturnOk() throws IOException {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("ab1234");
        String rolePersonId = "1";
        String personId = "999";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Satisfactorio");

        when(provider.changePassword(any(), any(), any(), any())).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>());

        // Assert
        verify(provider).changePassword(any(), any(), any(), any());
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenShortPasswordWhenChangePasswordThenReturnBadRequestException() throws IOException {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("ab12");

        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenLongPasswordWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("123456789012345a");

        String ip = "127.0.0.1";
        String deviceId = "123";
        String deviceUniqueId = "3cae84faa9b64750";
        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenSamePasswordWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("ab1234");
        changePasswordRequest.setNewPassword("ab1234");

        String ip = "127.0.0.1";
        String deviceId = "123";
        String deviceUniqueId = "3cae84faa9b64750";
        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.SAME_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenPasswordNotContainsLetterWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("ab1234");
        changePasswordRequest.setNewPassword("123456");

        String ip = "127.0.0.1";
        String deviceId = "123";
        String deviceUniqueId = "3cae84faa9b64750";
        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenPasswordNotContainsNumberWhenChangePasswordThenReturnBadRequestException() {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("ab1234");
        changePasswordRequest.setNewPassword("abcdef");

        String ip = "127.0.0.1";
        String deviceId = "123";
        String deviceUniqueId = "3cae84faa9b64750";
        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, rolePersonId, changePasswordRequest, new HashMap<>()));

        // Assert
        assertEquals(expectedResponse.getCode(), ((HandledException) exception).getCode());
        assertEquals(expectedResponse.getMessage(), exception.getMessage());
    }

    @Test
    void givenValidDataWhenGetContactInformation() throws IOException {
        // Arrange
        ContactResponse expected = GetContactResponseFixture.withDefault();
        when(provider.getContactInfo()).thenReturn(expected);

        // Act
        ContactResponse response = service.getContactInfo();

        // Assert
        verify(provider).getContactInfo();
        assertEquals(expected, response);
    }

    @Test
    void givenValidDataWhenGetPersonalInformation() throws IOException {
        // Arrange
        PersonalResponse expected = GetPersonalInformationResponseFixture.withDefault();
        when(personalInformationNetProvider.getPersonalInformation("123", map)).thenReturn(expected);

        // Act
        PersonalResponse response = service.getPersonalInformation("123", map);

        // Assert
        verify(personalInformationNetProvider).getPersonalInformation("123", map);
        assertEquals(expected, response);
    }

    @Test
    void givenValidPersonIdWhenGetBiometricStatus() throws IOException {
        // Arrange
        BiometricStatusMWResponse responseExpected = BiometricStatusMWResponseFixture.withDefault();
        when(provider.getBiometricsMW(any(), any())).thenReturn(responseExpected);

        // Act
        BiometricsResponse response = service.getBiometrics(123, new HashMap<>());

        // Assert
        verify(provider).getBiometricsMW(any(), any());
        assertEquals(BiometricsResponseFixture.withDefault(), response);
    }

    @Test
    void givenPersonIdWhenUpdateBiometricThenResponseExpected() throws IOException {
        // Arrange
        UpdateBiometricsResponse responseExpected = UpdateBiometricsResponseFixture.withDefault();
        UpdateBiometricsRequest request = UpdateBiometricsRequestFixture.withDefault();
        when(provider.updateBiometricsMW(any(), any(), any())).thenReturn(responseExpected);

        // Act
        UpdateBiometricsResponse response = service.updateBiometrics(123, request, new HashMap<>());

        // Assert
        verify(provider).updateBiometricsMW(any(), any(), any());
        assertEquals(UpdateBiometricsResponseFixture.withDefault(), response);
    }

    @Test
    void givenStatusNullWhenUpdateBiometricThenResponseBadRequest() throws IOException {
        // Arrange
        UpdateBiometricsRequest request = UpdateBiometricsRequest.builder().build();
        Map<String, String> map = new HashMap<>();

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.updateBiometrics(123, request, map);
        });

        // Assert
        assertTrue(exception.getCode().contains("BAD_REQUEST"));
    }

    @Test
    void givenPersonIdWhenGetEconomicActivityThenResponseExpected() throws IOException {
        // Arrange
        EconomicActivityResponse responseExpected = EconomicActivityResponseFixture.withDefault();
        when(personalInformationNetProvider.getEconomicalActivity(any())).thenReturn(responseExpected);

        // Act
        EconomicActivityResponse response = service.getEconomicActivity(123, new HashMap<>());

        // Assert
        verify(personalInformationNetProvider).getEconomicalActivity(any());
        assertEquals(EconomicActivityResponseFixture.withDefault(), response);
    }

    @Test
    void givenValidDataWhenGetDepartments() throws IOException {
        // Arrange
        DepartmentsNetResponse expectedNet = DepartmentsNetResponseFixture.withDefault();
        DepartmentsResponse expected = DepartmentsResponseFixture.withDefault();

        Mockito.when(apiFaceNetProvider.getDepartments(Mockito.any())).thenReturn(expectedNet);
        Mockito.when(iApiFaceMapper.mapToDepartmentsResponse(expectedNet)).thenReturn(expected);

        // Act
        DepartmentsResponse response = service.getDepartments(map);

        // Assert
        verify(apiFaceNetProvider).getDepartments(map);
        assertEquals(expected, response);
    }
}
