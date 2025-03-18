package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.certifications.CertificationConfigRequestFixture;
import bg.com.bo.bff.application.dtos.request.certifications.CertificationPriceRequestFixture;
import bg.com.bo.bff.application.dtos.request.certifications.SaveCertificationRequestFixture;
import bg.com.bo.bff.application.dtos.response.certifications.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.ICertificationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CertificationsControllerTest {

    @InjectMocks
    private CertificationsController controller;

    @Mock
    private ICertificationsService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void getCertificationsOK() throws Exception {
        List<CertificationTypesResponse> expected = CertificationResponseFixture.withDefaults();
        when(service.getCertificateTypes(any(), any())).thenReturn(expected);

        String url = "/api/v1/certifications/persons/1234/application/2";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getCertificateTypes(any(), any());
    }

    @Test
    void getAccountsOK() throws Exception {
        List<CertificationAccountsResponse> expected = CertificationResponseFixture.withDefaultsAccounts();
        when(service.getAccounts(any())).thenReturn(expected);

        String url = "/api/v1/certifications/accounts/persons/1234";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getAccounts(any());
    }

    @Test
    void getPrefExchRateOK() throws Exception {
        List<CertificationPrefExchRateResponse> expected = CertificationResponseFixture.withDefaultsPrefExchRate();
        when(service.getPreferredExchRate(any())).thenReturn(expected);

        String url = "/api/v1/certifications/pref-exchange/persons/1234";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getPreferredExchRate(any());
    }

    @Test
    void getHistoricOK() throws Exception {
        List<CertificationHistoryResponse> expected = CertificationResponseFixture.withDefaultsHistory();
        when(service.getCertificationsHistory(any())).thenReturn(expected);

        String url = "/api/v1/certifications/historic-certs/persons/1234";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getCertificationsHistory(any());
    }

    @Test
    void getConfigOK() throws Exception {
        String request = Util.objectToString(CertificationConfigRequestFixture.withDefaults());
        CertificationConfigResponse expected = CertificationResponseFixture.withDefaultsConfig();
        when(service.getConfig(any())).thenReturn(expected);

        String url = "/api/v1/certifications/configs";
        mockMvc.perform(post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getConfig(any());
    }

    @Test
    void getPriceOK() throws Exception {
        String request = Util.objectToString(CertificationPriceRequestFixture.withDefaults());
        CertificationPriceResponse expected = CertificationResponseFixture.withDefaultsPrice();
        when(service.getCertificationPrice(any())).thenReturn(expected);

        String url = "/api/v1/certifications/price";
        mockMvc.perform(post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(service).getCertificationPrice(any());
    }

    @Test
    void saveOK() throws Exception {
        String request = Util.objectToString(SaveCertificationRequestFixture.withDefaults());
        SaveCertificationResponse expected = CertificationResponseFixture.withDefaultsSave();
        when(service.saveCertRequest(any())).thenReturn(expected);

        String url = "/api/v1/certifications/save-request";
        mockMvc.perform(post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).saveCertRequest(any());
    }

}