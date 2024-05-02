package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IThirdAccountService;
import bg.com.bo.bff.services.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserApiTests {

    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private static String URL_CHANGE_PASSWORD = "/api/v1/users/999/change-password";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void givenValidDataWhenChangePasswordThenReturnOk() throws Exception {
        // Assert
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setNewPassword("1");
        request.setOldPassword("2");

        GenericResponse expected = new GenericResponse();
        String ip = "127.0.0.1";
        String deviceId = "3cae84faa9b64750";
        String userDeviceId = "123";
        String rolePersonId = "1";
        String personId = "999";

        when(userService.changePassword(personId, ip, deviceId, userDeviceId, rolePersonId, request)).thenReturn(expected);

        // Act
        mockMvc.perform(put(URL_CHANGE_PASSWORD)
                        .header("device-id", deviceId)
                        .header("role-person-id", rolePersonId)
                        .header("user-device-id", userDeviceId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andReturn();

        // Arrange
        verify(userService).changePassword(personId, ip, deviceId, userDeviceId, rolePersonId, request);
    }

    @Test
    void givenInvalidDataWhenChangePasswordThenReturnBadRequest() throws Exception {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();

        // Act and Assert
        mockMvc.perform(put(URL_CHANGE_PASSWORD)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
