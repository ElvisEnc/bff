package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequestFixture;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponseFixture;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.mappings.providers.pcc01.Pcc01Mapper;
import bg.com.bo.bff.mappings.providers.transfer.ITransferMapper;
import bg.com.bo.bff.mappings.providers.transfer.TransferMWtMapper;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequest;
import bg.com.bo.bff.providers.dtos.request.transfer.TransferMWRequestFixture;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponseFixture;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;
import bg.com.bo.bff.providers.interfaces.ITransferACHProvider;
import bg.com.bo.bff.providers.interfaces.ITransferProvider;
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
    private TransferMWtMapper transferMapper;
    @Mock
    private Pcc01Mapper pcc01Mapper;
    @Mock
    private ITransferMapper mapper;

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
        this.service = new TransferService(transferProvider, transferACHProvider, transferMapper, pcc01Mapper, mapper);
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

        TransferResponse response = service.transferOwnAccount("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferOwnAccount(any(), any());
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

        TransferResponse response = service.transferThirdAccount("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferThirdAccount(any(), any());
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

        TransferResponse response = service.transferAchAccount("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferACHProvider).transferAchAccount(any(), any());
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

        TransferResponse response = service.transferWallet("123456", "123", transferRequest, map);

        assertNotNull(response);
        assertEquals(expected, response);
        verify(transferProvider).transferWalletAccount(any(), any());
    }
}