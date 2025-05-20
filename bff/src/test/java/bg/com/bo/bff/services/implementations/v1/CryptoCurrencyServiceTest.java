package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.ExchangeOperationRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.GenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeOperationResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.GenerateVoucherResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.crypto.currency.CryptoCurrencyMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyRequestDTOFixture;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeOperationResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGenerateVoucherResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyResponseDTOFixture;
import bg.com.bo.bff.providers.implementations.TokenExternalProvider;
import bg.com.bo.bff.providers.implementations.feign.CryptoCurrencyFeignClient;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyError;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyServiceTest {

    @InjectMocks
    private CryptoCurrencyService service;

    @Mock
    private CryptoCurrencyFeignClient feignClient;

    @Mock
    private TokenExternalProvider tokenExternalProvider;

    @Spy
    private CryptoCurrencyMapper mapper = new CryptoCurrencyMapper();

    @Test
    void testRegisterAccount_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyPostRegisterAccountResponse response = CryptoCurrencyResponseDTOFixture.withDefaultRegisterAccount();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.createAccount(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        GenericResponse result = service.registerAccount("123456");

        // Assert
        assertEquals(CryptoCurrencyResponse.REGISTERED_SUCCESS.getMessage(), result.getMessage());
    }

    @Test
    void testRegisterAccount_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyPostRegisterAccountResponse response = CryptoCurrencyResponseDTOFixture.withDefaultRegisterAccountError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.createAccount(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.registerAccount("123456");
        });

        assertEquals(CryptoCurrencyError.USER_REGISTERED.getMessage(), ex.getMessage());
    }

    @Test
    void testGetAvailableBalance_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyGetAvailableBalanceResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAvailableBalance();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.availableBalance(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        AvailableBalanceResponse result = service.getAvailableBalance("123456");

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAvailableBalance_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyGetAvailableBalanceResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAvailableBalanceError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.availableBalance(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.getAvailableBalance("123456");
        });

        assertEquals(CryptoCurrencyError.ACCOUNT_NOT_FOUND.getMessage(), ex.getMessage());
    }

    @Test
    void testGetAccountEmail_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyGetAccountEmailResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAccountEmail();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.basicAccount(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        AccountEmailResponse result = service.getBasicAccount("123456");

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAccountEmail_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyGetAccountEmailResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAccountEmailError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.basicAccount(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.getBasicAccount("123456");
        });

        assertEquals(CryptoCurrencyError.EMAIL_NOT_FOUND.getMessage(), ex.getMessage());
    }

    @Test
    void testGetAccountExtract_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        AccountExtractRequest request = CryptoCurrencyRequestDTOFixture.withDefaultAccountExtract();
        CryptoCurrencyAccountExtractResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAccountExtract();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.accountExtract(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        List<AccountExtractResponse> result = service.getAccountExtract("123456", "123456", request);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAccountExtract_successDataNull() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        AccountExtractRequest request = CryptoCurrencyRequestDTOFixture.withDefaultAccountExtract();
        CryptoCurrencyAccountExtractResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAccountExtractNull();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.accountExtract(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        List<AccountExtractResponse> result = service.getAccountExtract("123456", "123456", request);

        // Assert
        assertNotNull(result);
    } 

    @Test
    void testGetAccountExtract_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        AccountExtractRequest request = CryptoCurrencyRequestDTOFixture.withDefaultAccountExtract();
        CryptoCurrencyAccountExtractResponse response = CryptoCurrencyResponseDTOFixture.withDefaultAccountExtractError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.accountExtract(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.getAccountExtract("123456", "123456", request);
        });

        assertEquals(CryptoCurrencyError.EXTRACT_NOT_FOUND.getMessage(), ex.getMessage());
    }

    @Test
    void testGetExchangeRate_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyExchangeRateResponse response = CryptoCurrencyResponseDTOFixture.withDefaultExchangeRate();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.exchangeRate(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        ExchangeRateResponse result = service.getExchangeRate("123456", "123456");

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetExchangeRate_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        CryptoCurrencyExchangeRateResponse response = CryptoCurrencyResponseDTOFixture.withDefaultExchangeRateError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.exchangeRate(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.getExchangeRate("123456", "123456");
        });

        assertEquals(CryptoCurrencyError.ERROR_EXCHANGE.getMessage(), ex.getMessage());
    }

    @Test
    void testPostExchangeOperation_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        ExchangeOperationRequest request = CryptoCurrencyRequestDTOFixture.withDefaultExchangeOperation();
        CryptoCurrencyExchangeOperationResponse response = CryptoCurrencyResponseDTOFixture.withDefaultExchangeOperation();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.exchangeOperation(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        ExchangeOperationResponse result = service.exchangeOperation("123456", "123456", request);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testPostExchangeOperation_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        ExchangeOperationRequest request = CryptoCurrencyRequestDTOFixture.withDefaultExchangeOperation();
        CryptoCurrencyExchangeOperationResponse response = CryptoCurrencyResponseDTOFixture.withDefaultExchangeOperationError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.exchangeOperation(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.exchangeOperation("123456", "123456", request);
        });

        assertEquals(CryptoCurrencyError.ERROR_EXCHANGE_OPERATION.getMessage(), ex.getMessage());
    }

    @Test
    void testPostGenerateVoucher_success() throws IOException {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        GenerateVoucherRequest request = CryptoCurrencyRequestDTOFixture.withDefaultGenerateVoucher();
        CryptoCurrencyGenerateVoucherResponse response = CryptoCurrencyResponseDTOFixture.withDefaultCurrencyGenerateVoucher();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.generateVoucher(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act
        GenerateVoucherResponse result = service.postGenerateVoucher("123456", request);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testPostGenerateVoucher_failure() {
        // Arrange
        ClientToken clientToken = CryptoCurrencyResponseDTOFixture.withDefaultClientToken();
        GenerateVoucherRequest request = CryptoCurrencyRequestDTOFixture.withDefaultGenerateVoucher();
        CryptoCurrencyGenerateVoucherResponse response = CryptoCurrencyResponseDTOFixture.withDefaultCurrencyGenerateVoucherError();
        when(tokenExternalProvider.generateAccountAccessToken(any()))
                .thenReturn(clientToken);

        when(feignClient.generateVoucher(eq("Bearer token"), any()))
                .thenReturn(response);

        // Act & Assert
        GenericException ex = assertThrows(GenericException.class, () -> {
            service.postGenerateVoucher("123456", request);
        });

        assertEquals(CryptoCurrencyError.ERROR_VOUCHER.getMessage(), ex.getMessage());
    }
}
