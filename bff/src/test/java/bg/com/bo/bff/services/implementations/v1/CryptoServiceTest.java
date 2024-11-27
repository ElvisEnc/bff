package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.providers.dtos.request.transfer.CryptoMWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.transfer.CryptoMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoServiceTest {
    @InjectMocks
    private CryptoService service;
    @Mock
    private ITransferProvider transferProvider;
    private final Map<String, String> map = new HashMap<>();

    @Test
    void givenCryptoDescriptionWhenValidateCryptoThenCryptoFoundResponse() throws IOException {
        // Arrange
        CryptoMWResponse cryptoMWResponse = TransferMWResponseFixture.withDefaultCryptoMWResponse();
        cryptoMWResponse.setCode("1");
        when(transferProvider.validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest(), (map))).thenReturn(cryptoMWResponse);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.validateCrypto("Test", map);
        });

        // Assert
        assertEquals(TransferMiddlewareError.MDWTRM_CRYPTO.getCode(), exception.getCode());
    }

    @Test
    void givenDescriptionWhenValidateCryptoThenPassValidation() throws IOException {
        // Arrange
        CryptoMWResponse cryptoMWResponse = TransferMWResponseFixture.withDefaultCryptoMWResponse();
        when(transferProvider.validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest(), (map))).thenReturn(cryptoMWResponse);

        // Act
        assertDoesNotThrow(() -> service.validateCrypto("Test", map));

        // Assert
        verify(transferProvider, times(1)).validateCrypto(any(CryptoMWRequest.class), eq(map));
    }
}