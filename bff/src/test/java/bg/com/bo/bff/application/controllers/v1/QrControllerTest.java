package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.qr.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRCodeRegenerateRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrRequestFixture;
import bg.com.bo.bff.application.dtos.response.qr.QrDecryptResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;
import bg.com.bo.bff.services.interfaces.IQrService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class QrControllerTest {
    private static final String GENERATE_QR_URL = "/api/v1/qrs/generate";
    private static final String REGENERATE_QR_URL = "/api/v1/qrs/regenerate";
    private static final String DECRYPT_QR_URL = "/api/v1/qrs/info";
    private static final String PAYMENT_QR_URL = "/api/v1/qrs/persons/{personId}/accounts/{accountId}/transfer";
    private MockMvc mockMvc;

    @Spy
    @InjectMocks
    private QrController controller;
    @Mock
    private IQrService service;
    @Mock
    private HttpServletRequest httpServletRequest;
    private ObjectMapper objectMapper;
    private final HttpHeaders headers = new HttpHeaders();
    private static final String DEVICE_ID = "42ebffbd7c30307d";
    private static final String DEVICE_IP = "127.0.0.1";
    private static final String DEVICE_NAME = "Android";
    private static final String GEO_POSITION_X = "12.265656";
    private static final String GEO_POSITION_Y = "12.454545";
    private static final String APP_VERSION = "1.0.0";
    private Integer personId = 123;

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
        Enumeration<String> enumerations = lists.elements();

        headers.add(DeviceMW.DEVICE_ID.getCode(), DEVICE_ID);
        headers.add(DeviceMW.DEVICE_IP.getCode(), DEVICE_IP);
        headers.add(DeviceMW.DEVICE_NAME.getCode(), DEVICE_NAME);
        headers.add(DeviceMW.GEO_POSITION_X.getCode(), GEO_POSITION_X);
        headers.add(DeviceMW.GEO_POSITION_Y.getCode(), GEO_POSITION_Y);
        headers.add(DeviceMW.APP_VERSION.getCode(), APP_VERSION);

        when(httpServletRequest.getHeaderNames()).thenReturn(enumerations);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
    }

    @Test
    void givenPersonCodeWhenGetQrsGeneratedAndPaidThenAllListQr() throws Exception {
        // Arrange
        QrListRequest requestMock = QrRequestFixture.withDefaultQrListRequest();

        QrListResponse responseExpected = QrResponseFixture.withDefaultQrListResponse();
        when(service.getQrGeneratedPaid(any(), any(), any())).thenReturn(responseExpected);

        // Act
        MvcResult result = mockMvc.perform(post("/api/v1/qrs/persons/{personId}", personId)
                        .content(objectMapper.writeValueAsString(requestMock))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalGenerated").value(1))
                .andReturn();
        String response = objectMapper.writeValueAsString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).getQrGeneratedPaid(any(), any(), any());
        assertNotNull(result);
    }

    @Test
    void givenQRCodeGenerateRequestWhenGenerateQRThenQRCodeGenerateResponse() throws Exception {
        // Arrange
        QRCodeGenerateResponse expected = QrResponseFixture.withDefaultQRCodeGenerateResponse();
        QRCodeGenerateRequest request = QrRequestFixture.whitDefaultQRCodeGenerateRequest();
        when(service.generateQR(any(), any())).thenReturn(expected);

        // Act

        MvcResult result = mockMvc.perform(post(GENERATE_QR_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).generateQR(any(), any());
        assertNotNull(result);
    }

    @Test
    void givenQRCodeRegenerateRequestWhenGenerateQRThenQRCodeGenerateResponse() throws Exception {
        // Arrange
        QRCodeGenerateResponse expected = QrResponseFixture.withDefaultQRCodeGenerateResponse();
        QRCodeRegenerateRequest request = QrRequestFixture.withDefaultQRCodeRegenerateRequest();
        when(service.regenerateQR(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(post(REGENERATE_QR_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).regenerateQR(any(), any());
        assertNotNull(result);
    }

    @Test
    void givenDataEncryptRequestWhenDecryptQRThenQRDecryptResponse() throws Exception {
        // Arrange
        QrDecryptResponse expected = QrResponseFixture.withDefaultQrDecryptResponse();
        QrDecryptRequest request = QrRequestFixture.withDefaultQrDecryptRequest();

        when(service.decryptQR(any(), any())).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(post(DECRYPT_QR_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).decryptQR(any(), any());
        assertNotNull(result);
    }

    @Test
    void givenQRPaymentRequestWhenQrPaymentThenQRPaymentMWResponse() throws Exception {
        // Arrange
        final String personId = "12333";
        final String accountId = "12333";
        QRPaymentMWResponse expected = QrResponseFixture.withDefaultQrPaymentMWResponse();
        QRPaymentRequest request = QrRequestFixture.withDefaultQRPaymentRequest();
        when(service.qrPayment(any(), any(), any(), any())).thenReturn(expected);

        //Act
        MvcResult result = mockMvc.perform(post(PAYMENT_QR_URL, personId, accountId)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(this.headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String response = objectMapper.writeValueAsString(expected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(response, actual);
        verify(service).qrPayment(any(), any(), any(), any());

    }


}