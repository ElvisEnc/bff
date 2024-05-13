package bg.com.bo.bff.application.controllers.v1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.services.implementations.v1.OtherAccountTransferService;
import bg.com.bo.bff.services.interfaces.ITransferService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bg.com.bo.bff.application.dtos.request.TransferRequestFixture;
import bg.com.bo.bff.application.dtos.response.TransferResponseFixture;
import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.implementations.v1.OwnAccountTransferService;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class TransferOwnAccountTest {

    private MockMvc mockMvc;
    @InjectMocks
    private TransferController controller;

    @Mock
    private OwnAccountTransferService services;

    @Mock
    private OtherAccountTransferService thirdServices;

    @Mock
    private ITransferService pcc01Service;
    @Mock
    private HttpServletRequest httpServletRequest;
    HttpHeaders headers = new HttpHeaders();

    private static String URL_POST_OWN = "/api/v1/transfers/persons/12345/accounts/12345678/own-account";
    private static String URL_POST_THIRD = "/api/v1/transfers/persons/1234567/third-accounts";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        this.headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        this.headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        this.headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        this.headers.add(DeviceMW.APP_VERSION.getCode(), "1.0.0");
        this.headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
    }

    @Test
    void transfer() throws Exception {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(services.transfer(any(), any(), any(), any())).thenReturn(expected);

        mockMvc.perform(post(URL_POST_OWN)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(services).transfer(any(), any(), any(), any());
    }

    @Test
    void transferThirdAccounts() throws Exception {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(thirdServices.transfer(any(), any())).thenReturn(expected);

        mockMvc.perform(post(URL_POST_THIRD).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(Util.objectToString(request, false))).andExpect(status().isOk()).andReturn();

        verify(thirdServices).transfer(any(), any());
    }

    @Test
    void givenRequestWhenPcc01ControlThenResponseValidate() throws IOException {
        // Arrange
        Pcc01Request pcc01Request = new Pcc01Request();
        Pcc01Response pcc01Response = new Pcc01Response();
        Mockito.when(pcc01Service.makeControl(pcc01Request)).thenReturn(pcc01Response);

        // Act
        ResponseEntity<Pcc01Response> response = controller.control(pcc01Request);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        Assertions.assertNotNull(response.getBody());
    }
}