package bg.com.bo.bff.application.dtos.response.qr;


import bg.com.bo.bff.providers.dtos.response.qr.QRPaymentMWResponse;
import bg.com.bo.bff.providers.dtos.response.qr.ReceiptDetailQrPayment;

public class QRPaymentMWResponseFixture {
    public static QRPaymentMWResponse withDefault(){

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
        QRPaymentMWResponse.MWResponse response =  new QRPaymentMWResponse.MWResponse("APPROVED", "89771419", "30565123",receiptDetailQrPayment );
        return QRPaymentMWResponse.builder()
                .data(response)
                .build();
    }
}
