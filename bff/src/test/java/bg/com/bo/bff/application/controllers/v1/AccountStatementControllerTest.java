package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementRequestFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.RegenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.RegenerateVoucherRequestFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.TransferMovementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementResponseFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.RegenerateVoucherResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.RegenerateVoucherResponseFixture;
import bg.com.bo.bff.application.dtos.response.account.statement.TransferMovementsResponse;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountStatementControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private AccountStatementController controller;
    @Mock
    private IAccountStatementService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    private ObjectMapper objectMapper;
    private final HttpHeaders headers = new HttpHeaders();

    private static final String URL_ACCOUNT_STATEMENT = "/api/v1/account-statement/234234/persons/234234";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        headers.add("topaz-channel", "2");
        headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        headers.add(DeviceMW.APP_VERSION.getCode(), "1.0.0");
        headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
        headers.add(DeviceMW.USER_DEVICE_ID.getCode(), "129");
    }

    @Test
    void givenPersonAndIdAccountWhenGetTransferMovementsThenListTransferMovementsResponse() throws Exception {
        // Arrange
        List<TransferMovementsResponse> expectedMock = AccountStatementResponseFixture.getDefaultTransferMovementsResponse();
        TransferMovementsRequest requestMock = AccountStatementRequestFixture.getDefaultTransferMovementsRequest();
        when(service.getTransferMovements(any(), any(), any(), any())).thenReturn(expectedMock);

        // Act
        String path = "/api/v1/account-statement/234234/persons/234234/movements";
        MvcResult result = mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedMock));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getTransferMovements(any(), any(), any(), any());
    }

    @Test
    void getVoucher() throws Exception {
        RegenerateVoucherResponse expectedMock = RegenerateVoucherResponseFixture.withDefaults();
        RegenerateVoucherRequest requestMock = RegenerateVoucherRequestFixture.withDefaults();
        when(service.getVoucher(any(), any())).thenReturn(expectedMock);

        // Act
        String path = "/api/v1/account-statement/voucher";
        MvcResult result = mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedMock));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getVoucher(any(), any());
    }
}
