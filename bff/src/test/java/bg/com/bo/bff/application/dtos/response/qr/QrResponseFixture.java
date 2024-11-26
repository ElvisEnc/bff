package bg.com.bo.bff.application.dtos.response.qr;

import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCDataResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRCodeGenerateResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.QRPaymentMWResponse;
import bg.com.bo.bff.providers.dtos.response.qr.mw.ReceiptDetailQrPayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class QrResponseFixture {
    public static QrListResponse withDefaultQrListResponse() {
        return QrListResponse.builder()
                .page(1)
                .totalByPage(10)
                .totalRegistries(10)
                .data(Collections.singletonList(
                        withDefaultQrGeneratedPaid()
                ))
                .build();
    }

    public static QrDecryptResponse withDefaultQrDecryptResponse() {
        return QrDecryptResponse.builder()
                .qrId("1234567890")
                .companyName("Companhia Ejemplo")
                .identificationNumber("987654321")
                .eif("EIF Ejemplo")
                .accountNumber("123-456-789")
                .currency("BOB")
                .amount(BigDecimal.valueOf(150.75))
                .reference("Ref123456")
                .expirationDate("2024-12-31")
                .singleUse("0")
                .serviceCode(18001)
                .freeField("Pago por servicios")
                .serialNumber("SN123456789")
                .bank("Banco Ejemplo")
                .build();
    }

    public static QrGeneratedPaid withDefaultQrGeneratedPaid() {
        return QrGeneratedPaid.builder()
                .customField("1")
                .qrId("24041901018000000159")
                .masterQrId("74305283")
                .identificationNumber("2920504")
                .businessName("UnitTest")
                .bankCode("1018")
                .bank("Banco")
                .currencyCode("068")
                .expiryDate("26/04/2024")
                .amount(BigDecimal.valueOf(100.50))
                .description("UnitTest")
                .singleUse(true)
                .serviceCode("1")
                .serialNumber("1")
                .operationType("1")
                .destinationAccountNumber(1310771861L)
                .status("2")
                .registrationDate("19/04/2024")
                .currencyDescription("UnitTest")
                .build();
    }

    public static QrGeneratedPaid withDefaultQrGeneratedPaid2() {
        return QrGeneratedPaid.builder()
                .customField("1")
                .qrId("24041901018000000159")
                .masterQrId("74305283")
                .identificationNumber("2920504")
                .businessName("UnitTest")
                .bankCode("1018")
                .bank("Banco")
                .currencyCode("068")
                .expiryDate("26/04/2024")
                .amount(BigDecimal.valueOf(100.50))
                .description("UnitTest")
                .singleUse(false)
                .serviceCode("1")
                .serialNumber("1")
                .operationType("2")
                .destinationAccountNumber(1310771861L)
                .status("2")
                .registrationDate("19/04/2024")
                .currencyDescription("UnitTest")
                .build();
    }

    public static QRPaymentMWResponse withDefaultQrPaymentMWResponse() {

        ReceiptDetailQrPayment receiptDetailQrPayment = ReceiptDetailQrPayment.builder()
                .accountingEntry("999000679")
                .accountingDate("30/05/2024")
                .accountingTime("18:08:20")
                .amountDebited("33")
                .amountCredited("33")
                .exchangeRateDebit("1")
                .exchangeRateCredit("1")
                .amount("33")
                .currency("BOB")
                .fromAccountNumber("1310124450")
                .fromHolder("EMPLEADO BANCO")
                .toAccountNumber("1000090553")
                .toHolder("Kelvin Leblanc")
                .description("Qr abiert")
                .fromCurrency("068")
                .toCurrency("068")
                .build();
        QRPaymentMWResponse.MWResponse response = new QRPaymentMWResponse.MWResponse("APPROVED", "89771419", "30565123", receiptDetailQrPayment);
        return QRPaymentMWResponse.builder()
                .data(response)
                .build();
    }

    public static QRPaymentMWResponse withDefaultQrPaymentMWResponsePending() {
        QRPaymentMWResponse.MWResponse response = new QRPaymentMWResponse.MWResponse("PENDING", "89771419", "30565123", ReceiptDetailQrPayment.builder().build());
        return QRPaymentMWResponse.builder()
                .data(response)
                .build();
    }

    public static QRCodeGenerateResponse withDefaultQRCodeGenerateResponse() {
        String futureDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return QRCodeGenerateResponse.builder()
                .data(new QRCDataResponse("123|123|123|123|123|123|123|123|" + futureDate + "|123|123|123|123|123"))
                .build();
    }

    public static QRCodeGenerateResponse withDefaultQRCodeGenerateResponseExpired() {
        return QRCodeGenerateResponse.builder()
                .data(new QRCDataResponse("123|123|123|123|123|123|123|123|2024-08-01|123|123|123|123|123"))
                .build();
    }
}