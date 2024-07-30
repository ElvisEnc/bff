package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoansRequestFixture;
import bg.com.bo.bff.application.dtos.request.qr.PeriodRequest;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ILoansService;
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

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoansControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private LoansController controller;
    @Mock
    private ILoansService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    Enumeration<String> enumerations;
    private ObjectMapper objectMapper;
    private final HttpHeaders headers = new HttpHeaders();
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_IP = "127.0.0.1";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
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
    void givenValidDataWhenGetListLoansByPersonIdThenListLoansResponse() throws Exception {

        //Arrange
        ListLoansRequest requestMock = LoansRequestFixture.withDefaultListLoansRequest();
        List<ListLoansResponse> expectedResponse = LoansResponseFixture.withDataDefaultListLoansResponse();
        when(service.getListLoansByPerson(any(), any(), any())).thenReturn(expectedResponse);
        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");

        // Act
        String path = "/api/v1/loans/persons/{personId}";
        MvcResult result = mockMvc.perform(post(path, "123456")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getListLoansByPerson(any(), any(), any());
    }

    @Test
    void givenLoanPaymentsRequestWhenGetLoanPaymentsThenResponseListLoanPayments() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoansRequestFixture.withDefaultLoanPaymentsRequest();
        List<LoanPaymentsResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanPaymentsResponse();
        when(service.getLoanPayments(any(), any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}/payments";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanPayments(any(), any(), any(), any());
    }

    @Test
    void givenBadFormatPeriodDateRequestWhenGetLoanPaymentsThenResponseBadRequest() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoanPaymentsRequest.builder()
                .loanNumber("123456")
                .filters(LoanPaymentsRequest.FilterLoanPayment.builder()
                        .date(PeriodRequest.builder().start("2023/11/22").end("2024/04/30").build())
                        .build())
                .refreshData(false)
                .build();
        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}/payments";
        mockMvc.perform(post(path, "123", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNullPeriodDateRequestWhenGetLoanPaymentsThenResponseBadRequest() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoanPaymentsRequest.builder()
                .loanNumber("123456")
                .filters(LoanPaymentsRequest.FilterLoanPayment.builder()
                        .date(PeriodRequest.builder().start("").end(null).build())
                        .build())
                .refreshData(false)
                .build();
        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}/payments";
        mockMvc.perform(post(path, "123", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenLoanInsurancePaymentsRequestWhenGetLoanPaymentsThenResponseListLoanInsurancePayments() throws Exception {
        //Arrange
        LoanPaymentsRequest requestMock = LoansRequestFixture.withDefaultLoanPaymentsRequest();
        List<LoanInsurancePaymentsResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanInsurancePaymentsResponse();
        when(service.getLoanInsurancePayments(any(), any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}/insurance-payments";
        MvcResult result = mockMvc.perform(post(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(requestMock))
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanInsurancePayments(any(), any(), any(), any());
    }

    @Test
    void givenLoanPlanRequestWhenGetLoanPlansThenResponseListLoanPlans() throws Exception {
        //Arrange
        List<LoanPlanResponse> expectedResponse = LoansResponseFixture.withDataDefaultLoanPlanResponse();
        when(service.getLoanPlans(any(), any(), any())).thenReturn(expectedResponse);

        // Act
        String path = "/api/v1/loans/{loanId}/persons/{personId}";
        MvcResult result = mockMvc.perform(get(path, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(ApiDataResponse.of(expectedResponse));
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(response, actual);
        verify(service).getLoanPlans(any(), any(), any());
    }
}