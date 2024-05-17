package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.TransferRequestFixture;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.response.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.application.dtos.response.TransferResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
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

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private TransferController controller;
    @Mock
    private ITransferService transferService;
    @Mock
    private HttpServletRequest httpServletRequest;
    HttpHeaders headers = new HttpHeaders();
    private final Integer personId = 123;
    private final Integer accountId = 10213215;
    private final Integer accountNumber = 121235468;

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
    void transferOwnAccount() throws Exception {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(transferService.transferOwnAccount(any(), any(), any(), any())).thenReturn(expected);

        String URL_POST_OWN = "/api/v1/transfers/persons/12345/accounts/12345678/own-account";
        mockMvc.perform(post(URL_POST_OWN)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(transferService).transferOwnAccount(any(), any(), any(), any());
    }

    @Test
    void transferThirdAccounts() throws Exception {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(transferService.transferThirdAccount(any(), any(), any(), any())).thenReturn(expected);

        String URL_POST_THIRD = "/api/v1/transfers/persons/12345/accounts/12345678/third-account";
        mockMvc.perform(post(URL_POST_THIRD)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(transferService).transferThirdAccount(any(), any(), any(), any());
    }

    @Test
    void transferACHAccounts() throws Exception {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(transferService.transferAchAccount(any(), any(), any(), any())).thenReturn(expected);

        String URL_POST_ACH = "/api/v1/transfers/persons/12345/accounts/12345678/ach";
        mockMvc.perform(post(URL_POST_ACH)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        verify(transferService).transferAchAccount(any(), any(), any(), any());
    }

    @Test
    void givePersonCodeAndAccountWhenTransferYoloThenReturnSuccess() throws Exception {
        // Arrange
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(transferService.transferWallet(any(), any(), any(), any(), any())).thenReturn(expected);

        // Act
        mockMvc.perform(post("/api/v1/transfers/persons/{personId}/accountId/{accountId}/accountNumber/{accountNumber}/wallet", personId, accountId, accountNumber)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        verify(transferService).transferWallet(any(), any(), any(), any(), any());
    }

    @Test
    void givenRequestWhenPcc01ControlThenResponseValidate() throws IOException {
        // Arrange
        Pcc01Request pcc01Request = new Pcc01Request();
        Pcc01Response pcc01Response = new Pcc01Response();
        Mockito.when(transferService.makeControl(pcc01Request)).thenReturn(pcc01Response);

        // Act
        ResponseEntity<Pcc01Response> response = controller.control(pcc01Request);

        // Assert
        assert response.getStatusCode().value() == HttpStatus.OK.value();
        Assertions.assertNotNull(response.getBody());
    }
}