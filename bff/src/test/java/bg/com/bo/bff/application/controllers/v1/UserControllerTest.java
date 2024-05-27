package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.GetContactResponseFixture;
import bg.com.bo.bff.application.dtos.response.GetPersonalInformationResponseFixture;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.user.PersonalResponse;
import bg.com.bo.bff.commons.enums.DeviceMW;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    private IUserService userService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @InjectMocks
    private UserController userController;
    private HttpHeaders headers;
    private static final String URL_CHANGE_PASSWORD = "/api/v1/users/999/change-password";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.headers = new HttpHeaders();
        this.headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        this.headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        this.headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        this.headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        this.headers.add(DeviceMW.APP_VERSION.getCode(), "1.0.0");
        this.headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void givenValidDataWhenChangePasswordThenReturnOk() throws Exception {
        // Arrange
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setNewPassword("1");
        request.setOldPassword("2");

        GenericResponse expected = new GenericResponse();
        String rolePersonId = "1";
        String personId = "999";

        when(userService.changePassword(any(),   any(), any(), any())).thenReturn(expected);

        // Act & Assert
        mockMvc.perform(put(URL_CHANGE_PASSWORD)
                        .header("middleware-channel","1")
                        .header("application-id","2")
                        .header("device-id","17177")
                        .header("device-ip","127.0.1.1")
                        .header("device-name","OS")
                        .header("geo-position-x","10101.12")
                        .header("geo-position-y","10101.12")
                        .header("app-version","1.0.1")
                        .header("person-role-id","10")
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
        ContactResponse expected = GetContactResponseFixture.withDefault();
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
        PersonalResponse expected = GetPersonalInformationResponseFixture.withDefault();
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
}
