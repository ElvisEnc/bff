package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.providers.dtos.requests.qr.CreditorAccount;
import bg.com.bo.bff.providers.dtos.requests.qr.DebtorAccount;
import bg.com.bo.bff.providers.dtos.requests.qr.InstructedAmount;
import bg.com.bo.bff.providers.dtos.requests.qr.OwnerAccount;
import bg.com.bo.bff.providers.dtos.requests.qr.QRPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.requests.qr.RiskMW;
import bg.com.bo.bff.providers.dtos.requests.qr.SupplementaryMWData;

public class QRPaymentMWRequestFixture {
    public static QRPaymentMWRequest withDefault(){

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
}
