package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequest;
import bg.com.bo.bff.application.dtos.request.QRCodeGenerateRequestFixture;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequestFixture;
import bg.com.bo.bff.application.dtos.response.QRCodeGenerateResponseFixture;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaidFixture;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponse;
import bg.com.bo.bff.application.dtos.response.qr.QrListResponseFixture;
import bg.com.bo.bff.providers.dtos.responses.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponse;
import bg.com.bo.bff.providers.dtos.responses.qr.QrListMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.mappings.qr.IQrMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QrServiceTest {
    @Spy
    @InjectMocks
    private QrService service;
    @Mock
    private IAchAccountProvider achAccountProvider;
    @Mock
    private  IQRProvider qrProvider;
    @Mock
    private IQrMapper iQrMapper;

    @Test
    void givePersonIdWhenGetQrListGeneratedAndPaidThenReturnSuccess() throws IOException {
        // Arrange
        QrListMWResponse mwResponseMock = QrListMWResponseFixture.withDefault();
        QrListResponse expectedResponse = QrListResponseFixture.withDefault();
        List<QrGeneratedPaid> expectedList = new ArrayList<>();
        expectedList.add(QrGeneratedPaidFixture.withDefault());

        Mockito.when(achAccountProvider.getListQrGeneratePaidMW(any(), any(), any())).thenReturn(mwResponseMock);
        Mockito.when(iQrMapper.convert(isA(QrGeneratedPaidMW.class))).thenReturn(QrGeneratedPaidFixture.withDefault());

        // Act
        List<QrGeneratedPaid> response = service.getListQrMW(QrListRequestFixture.withDefault(), 123, new HashMap<>(),"key", false);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse.getGenerated(), response);
        verify(achAccountProvider).getListQrGeneratePaidMW(any(),any(),any());
        verify(iQrMapper).convert(isA(QrGeneratedPaidMW.class));
    }
    @Test
    void givenQRCodeGenerateRequestWhenGenerateQRThenQRCodeGenerateResponse() throws IOException {
        // Arrange
        QRCodeGenerateResponse expected = QRCodeGenerateResponseFixture.withDefault();
        QRCodeGenerateRequest request = QRCodeGenerateRequestFixture.whitDefault();
        when(qrProvider.generate(any(),any())).thenReturn(expected);

        // Act
        QRCodeGenerateResponse actual = service.generateQR(request, new HashMap<>());

        // Assert
        Assertions.assertNotNull(actual);
        assertEquals(expected,actual);
        verify(qrProvider).generate(any(),any());
    }
}