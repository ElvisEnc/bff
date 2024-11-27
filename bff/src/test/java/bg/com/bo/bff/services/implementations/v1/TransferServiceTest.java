package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequestFixture;
import bg.com.bo.bff.application.dtos.response.transfer.Pcc01Response;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponseFixture;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.mappings.providers.transfer.ITransferMapper;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapper;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapperImpl;
import bg.com.bo.bff.mappings.providers.transfer.TransferMapper;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.transfer.*;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.models.enums.middleware.transfer.TransferMiddlewareError;
import bg.com.bo.bff.services.interfaces.ICryptoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    @InjectMocks
    private TransferService service;
    @Mock
    private ICryptoService cryptoService;
    @Mock
    private ITransferProvider transferProvider;
    @Mock
    private ITransferACHProvider transferACHProvider;
    @Spy
    private TransferMWtMapper transferMapper = new TransferMWtMapperImpl();
    @Spy
    private ITransferMapper mapper = new TransferMapper();

    private Map<String, String> map;

    @BeforeEach
    void setUp() {
        this.map = Map.of(
                DeviceMW.DEVICE_ID.getCode(), "1234",
                DeviceMW.DEVICE_IP.getCode(), "12344",
                DeviceMW.DEVICE_NAME.getCode(), "OS",
                DeviceMW.GEO_POSITION_X.getCode(), "121.11",
                DeviceMW.GEO_POSITION_Y.getCode(), "121.11",
                DeviceMW.APP_VERSION.getCode(), "1.0.0"
        );
        this.service = new TransferService(cryptoService, transferProvider, transferACHProvider, transferMapper, mapper);
    }

    @Test
    void givenDescriptionWhenTransferOwnAccountThenReturnCrytoFound() throws IOException {
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        doThrow(new GenericException(TransferMiddlewareError.MDWTRM_CRYPTO)).when(cryptoService).validateCrypto("Test", map);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.transferOwnAccount("123456", "123", transferRequest, map);
        });

        // Assert
        assertEquals(TransferMiddlewareError.MDWTRM_CRYPTO.getCode(), exception.getCode());
    }

    @Test
    void transferOwnAccount() throws IOException {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferMWResponse expectedMW = TransferMWResponseFixture.withDefault();
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferOwnAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        TransferResponse response = service.transferOwnAccount("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferOwnAccount(any(), any());
    }

    @Test
    void transferOwnAccountPending() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferMWResponse expectedMW = TransferMWResponseFixture.withDefaultTransferMWResponsePending();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferOwnAccount((requestMW), (map))).thenReturn(expectedMW);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.transferOwnAccount("123456", "123", transferRequest, map)
        );

        // Assert
        assertEquals("TRANSFER_PENDING", exception.getCode());
    }

    @Test
    void transferOwnAccountStatusNull() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferMWResponse expectedMW = TransferMWResponseFixture.withDefaultTransferMWResponseStatusNull();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferOwnAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        TransferResponse response = service.transferOwnAccount("123456", "123", transferRequest, map);

        // Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferOwnAccount(any(), any());
    }

    @Test
    void givenDescriptionWhenTransferThirdAccountThenReturnCrytoFound() throws IOException {
        TransferRequest transferRequest = TransferRequestFixture.withDefault();

        doThrow(new GenericException(TransferMiddlewareError.MDWTRM_CRYPTO)).when(cryptoService).validateCrypto("Test", map);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.transferThirdAccount("123456", "123", transferRequest, map);
        });

        // Assert
        assertEquals(TransferMiddlewareError.MDWTRM_CRYPTO.getCode(), exception.getCode());
    }

    @Test
    void transferThirdAccount() throws IOException {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferMWResponse expectedMW = TransferMWResponseFixture.withDefault();
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferThirdAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        TransferResponse response = service.transferThirdAccount("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferThirdAccount(any(), any());
    }

    @Test
    void transferThirdAccountPending() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferMWResponse expectedMW = TransferMWResponseFixture.withDefaultTransferMWResponsePending();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferThirdAccount((requestMW), (map))).thenReturn(expectedMW);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.transferThirdAccount("123456", "123", transferRequest, map);
        });

        // Assert
        assertEquals("TRANSFER_PENDING", exception.getCode());
    }

    @Test
    void transferThirdAccountStatusNull() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferMWResponse expectedMW = TransferMWResponseFixture.withDefaultTransferMWResponseStatusNull();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferThirdAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        TransferResponse response = service.transferThirdAccount("123456", "123", transferRequest, map);

        // Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferThirdAccount(any(), any());
    }

    @Test
    void givenDescriptionWhenTransferAchThenReturnCrytoFound() throws IOException {
        TransferRequest transferRequest = TransferRequestFixture.withDefault();

        doThrow(new GenericException(TransferMiddlewareError.MDWTRM_CRYPTO)).when(cryptoService).validateCrypto("Test", map);
        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.transferAchAccount("123456", "123", transferRequest, map);
        });

        // Assert
        assertEquals(TransferMiddlewareError.MDWTRM_CRYPTO.getCode(), exception.getCode());
    }

    @Test
    void transferACHAccount() throws IOException {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferAchMwResponse expectedMW = TransferMWResponseFixture.withDefaultACH();
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();

        when(transferMapper.convert(("ach"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferACHProvider.transferAchAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        TransferResponse response = service.transferAchAccount("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferACHProvider).transferAchAccount(any(), any());
    }

    @Test
    void transferACHAccountStatusNull() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferAchMwResponse expectedMW = TransferMWResponseFixture.withDefaultACHStatusNull();

        when(transferMapper.convert(("ach"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferACHProvider.transferAchAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        TransferResponse response = service.transferAchAccount("123456", "123", transferRequest, map);

        // Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferACHProvider).transferAchAccount(any(), any());
    }

    @Test
    void givenDescriptionWhenTransferWalletThenReturnCrytoFound() throws IOException {
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        doThrow(new GenericException(TransferMiddlewareError.MDWTRM_CRYPTO)).when(cryptoService).validateCrypto("Test", map);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.transferWallet("123456", "123", transferRequest, map);
        });

        // Assert
        assertEquals(TransferMiddlewareError.MDWTRM_CRYPTO.getCode(), exception.getCode());
    }

    @Test
    void givePersonCodeAndAccountWhenTransferWalletThenReturnSuccess() throws IOException {
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferWalletMWResponse expectedMW = TransferMWResponseFixture.withDefaultWallet();
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferWalletAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        TransferResponse response = service.transferWallet("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferWalletAccount(any(), any());
    }

    @Test
    void givePersonCodeAndAccountWhenTransferWalletThenReturnPending() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferWalletMWResponse expectedMW = TransferMWResponseFixture.withDefaultWalletPending();
        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferWalletAccount((requestMW), (map))).thenReturn(expectedMW);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        GenericException exception = assertThrows(GenericException.class, () -> {
            service.transferWallet("123456", "123", transferRequest, map);
        });

        // Assert
        assertEquals("TRANSFER_PENDING", exception.getCode());
    }

    @Test
    void givePersonCodeAndAccountWhenTransferWalletThenReturnNull() throws IOException {
        // Arrange
        TransferMWRequest requestMW = TransferMWRequestFixture.withDefault();
        TransferRequest transferRequest = TransferRequestFixture.withDefault();
        TransferResponse expected = TransferResponseFixture.withDefault();
        TransferWalletMWResponse expectedMW = TransferMWResponseFixture.withDefaultWalletStatusNull();

        when(transferMapper.convert(("own"), ("123456"), ("123"), (transferRequest))).thenReturn(requestMW);
        when(transferProvider.transferWalletAccount((requestMW), (map))).thenReturn(expectedMW);
        when(mapper.convert((expectedMW))).thenReturn(expected);
        doNothing().when(cryptoService).validateCrypto(TransferMWRequestFixture.withDefaultCryptoMWRequest().getDescription(), map);

        // Act
        TransferResponse response = service.transferWallet("123456", "123", transferRequest, map);

        // Assert
        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferWalletAccount(any(), any());
    }

    @Test
    void giveRequestWhenMakeControlReturnValidation() throws IOException {
        // Arrange
        Pcc01Request request = TransferRequestFixture.withDefaultPcc01Request();
        Pcc01Response responseExpected = TransferResponseFixture.withDefaultPcc01Response();
        when(transferProvider.validateControl(any(), any())).thenReturn(responseExpected);

        // Act
        Pcc01Response response = service.makeControl("123456", "654321", request, map);

        // Assert
        assertNotNull(response);
        assertEquals(responseExpected, response);
        verify(transferProvider).validateControl(any(), any());
    }
}