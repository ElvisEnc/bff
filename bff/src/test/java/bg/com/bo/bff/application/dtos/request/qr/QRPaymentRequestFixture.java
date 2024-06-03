package bg.com.bo.bff.application.dtos.request.qr;

public class QRPaymentRequestFixture {

    public static QRPaymentRequest withDefault() {

        TargetAccount targetAccount = TargetAccount.builder()
                .id("1000090553")
                .secondaryIdentification("1001")
                .name("Kelvin Leblanc")
                .build();
        Amount amount = Amount.builder()
                .currency("BOB")
                .amount("33")
                .build();
        SupplementaryData supplementaryData = SupplementaryData.builder()
                .idQr("24020201001000377739")
                .nroDni("0")
                .description("Qr abierto")
                .dueDate("2024-05-24")
                .typeUse("0")
                .serviceCode("0")
                .fields("campo libre")
                .serialNumber("1048DC9A668388944139515F6CFF04FE")
                .build();
        Risk risk = new Risk("QRPayment");

        return QRPaymentRequest.builder()
                .targetAccount(targetAccount)
                .amount(amount)
                .supplementaryData(supplementaryData)
                .risk(risk)
                .build();
    }
}
