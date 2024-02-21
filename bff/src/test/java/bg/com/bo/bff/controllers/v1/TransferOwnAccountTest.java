package bg.com.bo.bff.controllers.v1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import bg.com.bo.bff.controllers.models.TransferRequestFixture;
import bg.com.bo.bff.controllers.models.TransferResponseFixture;
import bg.com.bo.bff.controllers.request.TransferRequest;
import bg.com.bo.bff.controllers.response.TransferResponse;
import bg.com.bo.bff.model.util.Util;
import bg.com.bo.bff.services.v1.OwnAccountTransferService;

@ExtendWith(MockitoExtension.class)
public class TransferOwnAccountTest {

    private MockMvc mockMvc;
    @InjectMocks
    private TransferOwnAccountController controller;

    @Mock
    private OwnAccountTransferService services;

    private static String URL_POST = "/api/v1/own-account/transfer/persons/1234567";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void transfer() throws Exception {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferRequest request = TransferRequestFixture.withDefault();
        when(services.transfer(any(), any())).thenReturn(expected);

        mockMvc.perform(
                post(URL_POST)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false))
        ).andExpect(status().isOk()).andReturn();

        verify(services).transfer(any(), any());

    }
}