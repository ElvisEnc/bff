package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.qr.*;
import bg.com.bo.bff.application.dtos.response.qr.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.mappings.providers.qr.QrMapper;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrListMWResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QrMWResponseFixture;
import bg.com.bo.bff.providers.interfaces.IAchAccountProvider;
import bg.com.bo.bff.providers.interfaces.IQRProvider;
import bg.com.bo.bff.providers.interfaces.IQrTransactionProvider;
import bg.com.bo.bff.mappings.providers.qr.IQrMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QrServiceTest {
    @InjectMocks
    private QrService service;
    @Mock
    private IAchAccountProvider achAccountProvider;
    @Mock
    private IQrTransactionProvider qrTransactionProvider;
    @Mock
    private IQRProvider qrProvider;
    @Spy
    private IQrMapper iQrMapper = new QrMapper();
    @Mock
    private QrService self;
    private final Map<String, String> map = new HashMap<>();


    @Test
    void givenLoanPaymentsRequestWhenGetListLoanPaymentThenListLoanPaymentsResponse() throws IOException {
        //Arrange
        QrListRequest request = QrRequestFixture.withDefaultQrListRequest();
        List<QrGeneratedPaid> expectedList = new ArrayList<>();
        expectedList.add(QrResponseFixture.withDefaultQrGeneratedPaid());
        ReflectionTestUtils.setField(service, "self", self);
        when(self.getListQrMW(any(), any(), any(), any(), any())).thenReturn(expectedList);

        //Act
        QrListResponse response = service.getQrGeneratedPaid(request, "123", map);

        //Assert
        assertNotNull(response);
        verify(self).getListQrMW(any(), any(), any(), any(), any());
    }

    @Test
    void givePersonIdWhenGetQrListGeneratedAndPaidThenReturnSuccess() throws IOException {
        // Arrange
        QrListMWResponse mwResponseMock = QrMWResponseFixture.withDefaultQrListMWResponse();
        List<QrGeneratedPaid> expectedResponse = Arrays.asList(QrResponseFixture.withDefaultQrGeneratedPaid(), QrResponseFixture.withDefaultQrGeneratedPaid2());
        Mockito.when(achAccountProvider.getListQrGeneratePaidMW(any(), any(), any())).thenReturn(mwResponseMock);

        // Act
        List<QrGeneratedPaid> response = service.getListQrMW(QrRequestFixture.withDefaultQrListRequest(), "123", map, "key", false);

        // Assert
        Assertions.assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(achAccountProvider).getListQrGeneratePaidMW(any(), any(), any());
        verify(iQrMapper, times(2)).convert(isA(QrGeneratedPaidMW.class));
    }

    @Test
    void givenQRCodeGenerateRequestWhenGenerateQRThenQRCodeGenerateResponse() throws IOException {
        // Arrange
        QRCodeGenerateResponse expected = QrResponseFixture.withDefaultQRCodeGenerateResponse();
        QRCodeGenerateRequest request = QrRequestFixture.whitDefaultQRCodeGenerateRequest();

        when(qrProvider.generate(any(), any())).thenReturn(expected);

        // Act
        QRCodeGenerateResponse actual = service.generateQR(request, map);

        // Assert
        Assertions.assertNotNull(actual);
        assertEquals(expected, actual);
        verify(qrProvider).generate(any(), any());
    }

    @Test
    void givenQRCodeRegenerateRequestWhenGenerateQRThenQRCodeGenerateResponse() throws IOException {
        // Arrange
        QRCodeGenerateResponse expected = QrResponseFixture.withDefaultQRCodeGenerateResponse();
        QRCodeRegenerateRequest request = QrRequestFixture.withDefaultQRCodeRegenerateRequest();
        when(qrProvider.regenerate(any(), any())).thenReturn(expected);

        // Act
        QRCodeGenerateResponse actual = service.regenerateQR(request, map);

        // Assert
        Assertions.assertNotNull(actual);
        assertEquals(expected, actual);
        verify(qrProvider).regenerate(any(), any());
    }

    @Test
    void givenDataEncryptRequestWhenDecryptQRThenQRDecryptResponse() throws IOException {
        // Arrange
        QrDecryptRequest request = QrRequestFixture.withDefaultQrDecryptRequest();
        QRCodeGenerateResponse expected = QrResponseFixture.withDefaultQRCodeGenerateResponse();
        when(qrProvider.decrypt(any(), any())).thenReturn(expected);

        // Act
        QrDecryptResponse actual = service.decryptQR(request, map);

        // Assert
        assertNotNull(actual);
        verify(iQrMapper).convertDecrypt(any());
        verify(qrProvider).decrypt(any(), any());
        verify(iQrMapper).convertDecryptResponse(any());
    }

    @Test
    void givenDataEncryptRequestWhenDecryptQRThenQRDecryptResponseExpired() throws IOException {
        // Arrange
        QrDecryptRequest request = QrRequestFixture.withDefaultQrDecryptRequest();
        QRCodeGenerateResponse expected = QrResponseFixture.withDefaultQRCodeGenerateResponseExpired();
        when(qrProvider.decrypt(any(), any())).thenReturn(expected);

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.decryptQR(request, map)
        );

        // Assert
        assertEquals("QR_EXPIRED", exception.getCode());
    }

    @Test
    void givenQRPaymentRequestWhenQrPaymentThenQRPaymentMWResponse() throws IOException {
        // Arrange
        final String personId = "12333";
        final String accountId = "12333";
        final QRPaymentMWResponse expected = QrResponseFixture.withDefaultQrPaymentMWResponse();
        QRPaymentRequest request = QrRequestFixture.withDefaultQRPaymentRequest();

        when(qrTransactionProvider.qrPayment(any(), any())).thenReturn(expected);

        // Act
        final QRPaymentMWResponse actual = service.qrPayment(request, personId, accountId, map);

        //Assert
        assertEquals(expected, actual);
        verify(qrTransactionProvider).qrPayment(any(), any());
    }

    @Test
    void givenQRPaymentRequestWhenQrPaymentThenQRPaymentMWResponsePending() throws IOException {
        // Arrange
        QRPaymentRequest request = QrRequestFixture.withDefaultQRPaymentRequest();
        QRPaymentMWResponse expected = QrResponseFixture.withDefaultQrPaymentMWResponsePending();

        when(qrTransactionProvider.qrPayment(any(), any())).thenReturn(expected);

        // Act
        GenericException exception = assertThrows(GenericException.class, () ->
                service.qrPayment(request, "123", "123", map)
        );

        //Assert
        assertEquals("TRANSFER_PENDING", exception.getCode());
    }
}