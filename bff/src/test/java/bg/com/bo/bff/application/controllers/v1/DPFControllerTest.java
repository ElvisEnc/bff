package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.DPFListResponse;
import bg.com.bo.bff.application.dtos.response.DPFResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.services.interfaces.IDPFService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class DPFControllerTest {
    private static final String GET_LIST_DPFS = "/api/v1//dpfs/persons/{personId}";

    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private DPFController controller;
    @Mock
    private IDPFService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    HttpHeaders headers = new HttpHeaders();
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        headers.add(DeviceMW.DEVICE_ID.getCode(), "121j1hjh1jh1jh");
        headers.add(DeviceMW.DEVICE_NAME.getCode(), "Android");
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), "1101,1");
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), "11101,1");
        headers.add(DeviceMW.APP_VERSION.getCode(),"1.0.0");
        headers.add(DeviceMW.DEVICE_IP.getCode(), "127.0.0.1");
    }

    @Test
    void givenPersonIdWhenGetDPFSThenListDPFs() throws Exception {
        // Arrange
        String personId = "169494";
        DPFListResponse mockResponse = DPFResponseFixture.withDefault();
        when(service.getDPFsList(any(),any(),any())).thenReturn(mockResponse);

        // Act
        mockMvc.perform(get(GET_LIST_DPFS, personId)
                        .headers(this.headers)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].numDPF").value(mockResponse.getData().get(0).getNumDPF()))
                .andExpect(jsonPath("$.data[0].numDpfBGA").value(mockResponse.getData().get(0).getNumDpfBGA()))
                .andExpect(jsonPath("$.data[0].clientName").value(mockResponse.getData().get(0).getClientName()))
                .andExpect(jsonPath("$.data[0].capital").value(mockResponse.getData().get(0).getCapital()))
                .andExpect(jsonPath("$.data[0].interes").value(mockResponse.getData().get(0).getInteres()))
                .andExpect(jsonPath("$.data[0].total").value(mockResponse.getData().get(0).getTotal()))
                .andExpect(jsonPath("$.data[0].codeCurrency").value(mockResponse.getData().get(0).getCodeCurrency()))
                .andExpect(jsonPath("$.data[0].dischargeDate").value(mockResponse.getData().get(0).getDischargeDate()))
                .andExpect(jsonPath("$.data[0].expirationDate").value(mockResponse.getData().get(0).getExpirationDate()))
                .andExpect(jsonPath("$.data[0].term").value(mockResponse.getData().get(0).getTerm()))
                .andExpect(jsonPath("$.data[0].rate").value(mockResponse.getData().get(0).getRate()))
                .andExpect(jsonPath("$.data[0].paymentFrequency").value(mockResponse.getData().get(0).getPaymentFrequency()));
        // Assert
        verify(service).getDPFsList(any(),any(),any());
    }
}
