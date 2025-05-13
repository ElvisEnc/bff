package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.HeadersDataFixture;
import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.CryptoCurrencyRequestFixture;
import bg.com.bo.bff.application.dtos.request.crypto.currency.ExchangeOperationRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.GenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.CryptoCurrencyResponseFixture;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeOperationResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.GenerateVoucherResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.ICryptoCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyControllerTest {
    private MockMvc mockMvc;
    @Spy
    @InjectMocks
    private CryptoCurrencyController controller;
    @Mock
    private ICryptoCurrencyService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

        MockHttpServletRequest mockRequest = HeadersDataFixture.getMockHttpServletRequest();

        controller.setHttpServletRequest(mockRequest);
    }

    @Test
    void givenPersonIdWhenRegisterAccountThenReturnSuccess() throws Exception {
        // Arrange
        GenericResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultGeneric();
        when(service.registerAccount(any())).thenReturn(responseExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/account-create";
        MvcResult result = mockMvc.perform(post(urlCryptoCurrency, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).registerAccount(any());
    }

    @Test
    void givenPersonIdWhenGetAvailableBalanceThenReturnSuccess() throws Exception {
        // Arrange
        AvailableBalanceResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultAvailableBalance();
        when(service.getAvailableBalance(any())).thenReturn(responseExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/available-balance";
        MvcResult result = mockMvc.perform(get(urlCryptoCurrency, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getAvailableBalance(any());
    }

    @Test
    void givenPersonIdWhenGetAccountEmailThenReturnSuccess() throws Exception {
        // Arrange
        AccountEmailResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultAccountEmail();
        when(service.getAccountEmail(any())).thenReturn(responseExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/account-email";
        MvcResult result = mockMvc.perform(get(urlCryptoCurrency, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getAccountEmail(any());
    }

    @Test
    void givenPersonIdWhenGetAccountExtractThenReturnSuccess() throws Exception {
        // Arrange
        AccountExtractRequest request = CryptoCurrencyRequestFixture.withDefaultAccountExtract();
        AccountExtractResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultAccountExtract();
        List<AccountExtractResponse> responseListExpected = new ArrayList<>();
        responseListExpected.add(responseExpected);
        when(service.getAccountExtract(any(), any(), any())).thenReturn(responseListExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/accounts/{accountId}/account-extract";
        MvcResult result = mockMvc.perform(post(urlCryptoCurrency, "123", "12345")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).getAccountExtract(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenGetExchangeRateThenReturnSuccess() throws Exception {
        // Arrange
        ExchangeRateResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultExchangeRate();
        when(service.getExchangeRate(any(), any())).thenReturn(responseExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/currency/{currencyId}/exchange-rate";
        MvcResult result = mockMvc.perform(get(urlCryptoCurrency, "123", "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String expected = Util.objectToString(responseExpected);
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertNotNull(result);
        assertEquals(expected, actual);
        verify(service).getExchangeRate(any(), any());
    }

    @Test
    void givenPersonIdWhenExchangeOperationThenReturnSuccess() throws Exception {
        // Arrange
        ExchangeOperationRequest request = CryptoCurrencyRequestFixture.withDefaultExchangeOperation();
        ExchangeOperationResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultExchangeOperation();
        when(service.exchangeOperation(any(), any(), any())).thenReturn(responseExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/accounts/{accountId}/exchange-operation";
        MvcResult result = mockMvc.perform(post(urlCryptoCurrency, "123", "12345")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).exchangeOperation(any(), any(), any());
    }

    @Test
    void givenPersonIdWhenPostGenerateVoucherThenReturnSuccess() throws Exception {
        // Arrange
        GenerateVoucherRequest request = CryptoCurrencyRequestFixture.withDefaultGenerateVoucher();
        GenerateVoucherResponse responseExpected = CryptoCurrencyResponseFixture.withDefaultGenerateVoucher();
        when(service.postGenerateVoucher(any(), any())).thenReturn(responseExpected);

        // Act
        String urlCryptoCurrency = "/api/v1/cryptocurrency/persons/{personId}/generate-voucher";
        MvcResult result = mockMvc.perform(post(urlCryptoCurrency, "123")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.objectToString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        assertNotNull(result);
        verify(service).postGenerateVoucher(any(), any());
    }

}
