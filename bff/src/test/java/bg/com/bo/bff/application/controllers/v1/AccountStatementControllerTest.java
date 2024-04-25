package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.ExtractRequestFixture;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponseFixture;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
 class AccountStatementControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private AccountStatementController controller;

    @Mock
    private IAccountStatementService service;

    private static final String URL_ACCOUNT_STATEMENT = "/api/v1/account-statement/234234/persons/234234";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void GivenExtractWhenRequestIsValidThenReturnExtractData() throws Exception {
        // Arrange
        ExtractRequest request = ExtractRequestFixture.withDefault();
        ExtractDataResponse expectedResponse = ExtractDataResponseFixture.withDefault();
        Mockito.when(service.getAccountStatement(Mockito.any(), Mockito.any())).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(post(URL_ACCOUNT_STATEMENT)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request, false)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Mockito.verify(service).getAccountStatement(any(), any());
    }
}
