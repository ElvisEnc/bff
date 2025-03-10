package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationAccountsResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationResponseFixture;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CertificationsControllerTest {

    @InjectMocks
    private CertificationsController controller;

    @Mock
    private ICertificationsService service;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

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

}