package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.response.certifications.*;
import bg.com.bo.bff.services.interfaces.ICertificationsService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

}