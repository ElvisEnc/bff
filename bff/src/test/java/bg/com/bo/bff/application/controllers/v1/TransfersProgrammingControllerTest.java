package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.PaymentsPlanResponseFixture;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponseFixture;
import bg.com.bo.bff.services.implementations.v1.TransferProgrammingService;
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
class TransfersProgrammingControllerTest {

    @InjectMocks
    private TransfersProgrammingController controller;

    @Mock
    private TransferProgrammingService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void getTransfersOK() throws Exception {
        List<ProgrammedTransfersResponse> expected = ProgrammedTransfersResponseFixture.withDefaults();
        when(service.getTransfers(any())).thenReturn(expected);

        String url = "/api/v1/transfer-programming/persons/1234";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getTransfers(any());
    }

    @Test
    void getPaymentsPlanOK() throws Exception {
        List<PaymentsPlanResponse> expected = PaymentsPlanResponseFixture.withDefaults();
        when(service.getPaymentsPlan(any())).thenReturn(expected);

        String url = "/api/v1/transfer-programming/payments-plan/persons/12345/transfer/518";
        mockMvc.perform(get(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service).getPaymentsPlan( any());
    }

}











