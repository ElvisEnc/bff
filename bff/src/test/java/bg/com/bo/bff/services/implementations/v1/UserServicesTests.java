package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.GetContactResponseFixture;
import bg.com.bo.bff.application.dtos.response.GetPersonalInformationResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IPersonalInformationNetProvider;
import bg.com.bo.bff.services.implementations.v1.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServicesTests {

    @Mock
    private ILoginMiddlewareProvider provider;

    @Mock
    private IPersonalInformationNetProvider personalInformationNetProvider;

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
        this.service = new UserService(provider, personalInformationNetProvider);
    }

    @Test
    void givenValidDataWhenChangePasswordThenReturnOk() throws IOException {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("ab1234");

        String ip = "127.0.0.1";
        String deviceId = "123";
        String deviceUniqueId = "3cae84faa9b64750";
        String rolePersonId = "1";
        String personId = "999";

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setCode("SUCCESS");
        expectedResponse.setMessage("Satisfactorio");

        when(provider.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest)).thenReturn(expectedResponse);

        // Act
        GenericResponse response = service.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest);

        // Assert
        verify(provider).changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest);
        assertEquals(expectedResponse, response);
    }

    @Test
    void givenShortPasswordWhenChangePasswordThenReturnBadRequestException() throws IOException {
        // Arrange
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword("123456");
        changePasswordRequest.setNewPassword("ab12");

        String ip = "127.0.0.1";
        String deviceId = "123";
        String deviceUniqueId = "3cae84faa9b64750";
        String rolePersonId = "1";
        String personId = "999";

        HandledException expectedResponse = new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD);

        // Act
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest));

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
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest));

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
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest));

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
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest));

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
        Exception exception = assertThrows(Exception.class, () -> service.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest));

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
}
