package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.qr.QrListRequestFixture;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaidFixture;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.mappings.qr.IQrMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QrServiceTest {
    private QrService service;
    @Mock
    private IAchAccountProvider achAccountProvider;
    @Mock
    private IQrMapper iQrMapper;
    @BeforeEach
    void init() {
        service = new QrService(achAccountProvider, iQrMapper);
    }


    @Test
    void givePersonIdWhenGetQrListGeneratedAndPaidThenReturnSuccess() throws IOException {
        // Arrange
        QrListMWResponse mwResponseMock = QrListMWResponseFixture.withDefault();
        QrListResponse expectedResponse = QrListResponseFixture.withDefault();
        List<QrGeneratedPaid> expectedList = new ArrayList<>();
        expectedList.add(QrGeneratedPaidFixture.withDefault());

        Mockito.when(achAccountProvider.getListQrGeneratePaidMW(any(), any(), any())).thenReturn(mwResponseMock);
        Mockito.when(iQrMapper.convert(any())).thenReturn(QrGeneratedPaidFixture.withDefault());

        // Act
        List<QrGeneratedPaid> response = service.getListQrMW(QrListRequestFixture.withDefault(), 123, new HashMap<>(),"key", false);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse.getGenerated(), response);
        verify(achAccountProvider).getListQrGeneratePaidMW(any(),any(),any());
        verify(iQrMapper).convert(any());
    }
}