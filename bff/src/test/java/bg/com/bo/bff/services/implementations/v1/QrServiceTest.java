package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.*;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequest;
import bg.com.bo.bff.application.dtos.request.qr.QRPaymentRequestFixture;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequest;
import bg.com.bo.bff.application.dtos.request.qr.QrDecryptRequestFixture;
import bg.com.bo.bff.application.dtos.request.qr.QrListRequestFixture;
import bg.com.bo.bff.application.dtos.response.QRCodeGenerateResponseFixture;
import bg.com.bo.bff.application.dtos.response.qr.*;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.providers.dtos.response.qr.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.QRPaymentMWResponse;
import bg.com.bo.bff.providers.dtos.response.qr.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.response.qr.QrListMWResponse;
import bg.com.bo.bff.providers.dtos.response.qr.QrListMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.interfaces.IQrTransactionProvider;
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
    private IQrTransactionProvider qrTransactionProvider;
    @Mock
    private  IQRProvider qrProvider;
    @Mock
    private IQrMapper iQrMapper;

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
        this.service = new QrService(achAccountProvider, iQrMapper, qrProvider, qrTransactionProvider);
    }

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

    @Test
    void givenQRCodeRegenerateRequestWhenGenerateQRThenQRCodeGenerateResponse() throws IOException {
        // Arrange
        QRCodeGenerateResponse expected = QRCodeGenerateResponseFixture.withDefault();
        QRCodeRegenerateRequest request = QRCodeRegenerateRequestFixture.withDefault();
        when(qrProvider.regenerate(any(),any())).thenReturn(expected);

        // Act
        QRCodeGenerateResponse actual = service.regenerateQR(request, new HashMap<>());

        // Assert
        Assertions.assertNotNull(actual);
        assertEquals(expected,actual);
        verify(qrProvider).regenerate(any(),any());
    }

    @Test
    void givenDataEncryptRequestWhenDecryptQRThenQRDecryptResponse() throws IOException {
        // Arrange
        QRCodeGenerateResponse expected = QRCodeGenerateResponseFixture.withDefault();
        QrDecryptRequest request = QrDecryptRequestFixture.withDefault();

        Mockito.when(iQrMapper.convertDecrypt(isA(QrDecryptRequest.class))).thenReturn(QRCodeRegenerateMWRequestFixture.withDefault());
        Mockito.when(qrProvider.decrypt(any(), any())).thenReturn(expected);
        Mockito.when(iQrMapper.convertDecryptResponse(isA(QRCodeGenerateResponse.class))).thenReturn(QrDecryptResponseFixture.withDefault());

        when(qrProvider.decrypt(any(),any())).thenReturn(expected);

        // Act
        QrDecryptResponse actual = service.decryptQR(request, map);

        // Assert
        Assertions.assertNotNull(actual);
        assertEquals(iQrMapper.convertDecryptResponse(expected),actual);
        verify(qrProvider).decrypt(any(),any());
    }

    @Test
    void givenQRPaymentRequestWhenQrPaymentThenQRPaymentMWResponse() throws IOException {
        // Arrange
        final String personId= "12333";
        final String accountId= "12333";
        final QRPaymentMWResponse expected = QRPaymentMWResponseFixture.withDefault();
        QRPaymentRequest request = QRPaymentRequestFixture.withDefault();
        when(qrTransactionProvider.qrPayment(any(), any())).thenReturn(expected);

        // Act
        final QRPaymentMWResponse actual = service.qrPayment(request, personId, accountId, map);

        //Assert

        assertEquals(expected,actual);
        verify(qrTransactionProvider).qrPayment(any(),any());

    }
}