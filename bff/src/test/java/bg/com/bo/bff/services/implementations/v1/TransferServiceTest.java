package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.TransferRequestFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.response.TransferMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.TransferResponseMD;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
import bg.com.bo.bff.providers.interfaces.ITransferYoloNetProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    private TransferService service;
    @Mock
    private ITransferProvider transferProvider;
    @Mock
    private ITransferACHProvider transferACHProvider;
    @Mock
    private ITransferYoloNetProvider transferYoloNetProvider;
    private Map<String, String> map;
    private final Integer personId = 123;
    private final Integer accountId = 10213215;
    private final Integer accountNumber = 121235468;

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
        this.service = new TransferService(transferProvider, transferACHProvider, transferYoloNetProvider);
    }

    @Test
    void transferOwnAccount() throws IOException {
        TransferResponseMD expected = TransferMWResponseFixture.withDefault();
        when(transferProvider.transferOwnAccount(any(), any(), any(), any())).thenReturn(expected);

        TransferResponseMD response = service.transferOwnAccount("123456", "123", TransferRequestFixture.withDefault(), map);
        assertNotNull(response);

        verify(transferProvider).transferOwnAccount(any(), any(), any(), any());
    }

    @Test
    void transferThirdAccount() throws IOException {
        TransferResponseMD expected = TransferMWResponseFixture.withDefault();
        when(transferProvider.transferThirdAccount(any(), any(), any(), any())).thenReturn(expected);

        TransferResponseMD response = service.transferThirdAccount("123456", "123", TransferRequestFixture.withDefault(), map);
        assertNotNull(response);

        verify(transferProvider).transferThirdAccount(any(), any(), any(), any());
    }

    @Test
    void transferACHAccount() throws IOException {
        TransferResponseMD expected = TransferMWResponseFixture.withDefault();
        when(transferACHProvider.transferAchAccount(any(), any(), any(), any())).thenReturn(expected);

        TransferResponseMD response = service.transferAchAccount("123456", "123", TransferRequestFixture.withDefault(), map);
        assertNotNull(response);

        verify(transferACHProvider).transferAchAccount(any(), any(), any(), any());
    }

    @Test
    void givePersonCodeAndAccountWhenTransferWalletThenReturnSuccess() throws IOException {
        // Arrange
        TransferResponseMD expected = TransferMWResponseFixture.withDefault();
        when(transferYoloNetProvider.transferToYolo(any(), any(), any(), any())).thenReturn(expected);

        // Act
        TransferResponseMD response = service.transferWallet(personId,accountId,accountNumber, TransferRequestFixture.withDefault(),map);

        // Assert
        assertNotNull(response);
        verify(transferYoloNetProvider).transferToYolo(any(), any(), any(), any());
    }
}