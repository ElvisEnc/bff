package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import bg.com.bo.bff.application.dtos.request.commons.SearchCriteriaRequest;
import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.providers.dtos.request.qr.mw.*;

import java.util.Arrays;

public class QrRequestFixture {
    public static QrListRequest withDefaultQrListRequest() {
        return QrListRequest.builder()
                .operationType("1")
                .filters(QrListFilterRequest.builder()
                        .period(PeriodRequest.builder()
                                .start("2023-11-22")
                                .end("2024-04-30")
                                .build())
                        .searchCriteria(SearchCriteriaRequest.builder()
                                .parameters(Arrays.asList(
                                        "description"
                                ))
                                .value("UnitTest")
                                .build())
                        .build())
                .pagination(PaginationRequest.builder()
                        .page(1)
                        .pageSize(10)
                        .build())
                .order(OrderRequest.builder()
                        .field("REGISTRATION_DATE")
                        .desc(true)
                        .build())
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequest() {
        return PeriodRequest.builder()
                .start("2024-06-13")
                .end("2024-06-17")
                .build();
    }

    public static QrDecryptRequest withDefaultQrDecryptRequest(){
        return QrDecryptRequest.builder()
                .data("data")
                .build();
    }

    public static QRPaymentMWRequest withDefaultQRPaymentMWRequest(){

        OwnerAccount ownerAccount = OwnerAccount.builder()
                .schemeName("PersonId")
                .personId("610761")
                .build();

        InstructedAmount instructedAmount = InstructedAmount.builder()
                .currency("BOB")
                .amount("33")
                .build();

        DebtorAccount debtorAccount = DebtorAccount.builder()
                .schemeName("AccountId")
                .identification("2054869")
                .build();

        CreditorAccount creditorAccount= CreditorAccount.builder()
                .schemeName("AchAccountNumber")
                .identification("1000090553")
                .secondaryIdentification("1001")
                .name("Kelvin Leblanc")
                .build();

        SupplementaryMWData supplementaryData = SupplementaryMWData.builder()
                .idQr("24020201001000377739")
                .nroDni("0")
                .description("Qr abierto")
                .dueDate("2024-05-24")
                .typeUse("0")
                .serviceCode("0")
                .fields("campo libre")
                .serialNumber("1048DC9A668388944139515F6CFF04FE")
                .build();

        RiskMW risk = new RiskMW("QRPayment");

        return  QRPaymentMWRequest.builder()
                .ownerAccount(ownerAccount)
                .instructedAmount(instructedAmount)
                .debtorAccount(debtorAccount)
                .creditorAccount(creditorAccount)
                .supplementaryData(supplementaryData)
                .risk(risk)
                .build();
    }

    public static QRPaymentRequest withDefaultQRPaymentRequest() {

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
                .allowsDuplicate("N")
                .build();
        Risk risk = new Risk("QRPayment");

        return QRPaymentRequest.builder()
                .targetAccount(targetAccount)
                .amount(amount)
                .supplementaryData(supplementaryData)
                .risk(risk)
                .build();
    }

    public static QRCodeGenerateRequest whitDefaultQRCodeGenerateRequest(){
        return QRCodeGenerateRequest.builder()
                .companyName("example")
                .amount("1.00")
                .currency("068")
                .reference("adas")
                .typeDueDate("1")
                .codService("0")
                .singleUse("1310771861")
                .accountNumber("1")
                .field("1")
                .serialNumber("1")
                .operationType("1")
                .personId("172628")
                .userRegister("1")
                .typeReturn("S")
                .formatImage("1")
                .build();

    }

    public static QRCodeRegenerateRequest withDefaultQRCodeRegenerateRequest(){
        return QRCodeRegenerateRequest
                .builder()
                .idQr("24042201018000000107111")
                .companyName("Solares")
                .identificationNumber("13639336111")
                .eif("1018")
                .accountNumber("1310771861111")
                .currency("BOB")
                .amount("50066")
                .reference("adas")
                .expirationDate("2024-04-29")
                .singleUse("0")
                .codService("1234")
                .field("casa")
                .build();
    }
}