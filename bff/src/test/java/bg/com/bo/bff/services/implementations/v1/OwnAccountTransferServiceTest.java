package bg.com.bo.bff.services.implementations.v1;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.responses.TransferMWResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.TransferResponseMD;
import bg.com.bo.bff.providers.implementations.GenerateImageProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import bg.com.bo.bff.application.dtos.request.TransferRequestFixture;
import bg.com.bo.bff.application.dtos.response.TransferResponse;
import bg.com.bo.bff.providers.interfaces.ITransferOwnAccountProvider;

@ExtendWith(MockitoExtension.class)
class OwnAccountTransferServiceTest {

    @Mock
    private OwnAccountTransferService service;

    @Mock
    private ITransferOwnAccountProvider provider;

    @Mock
    private GenerateImageProvider providerImage;
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
        this.service = new OwnAccountTransferService(provider, providerImage);
    }

    @Test
    void transfer() throws IOException {
        TransferResponseMD expected = TransferMWResponseFixture.withDefault();
        when(provider.transfer(any(), any(), any(), any())).thenReturn(expected);

        TransferResponse response = service.transfer("123456", "123", TransferRequestFixture.withDefault(), map);
        assertNotNull(response);

        verify(provider).transfer(any(), any(), any(), any());

    }
}