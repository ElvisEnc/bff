package bg.com.bo.bff.providers.dtos.request.remittance;

import bg.com.bo.bff.providers.dtos.request.remittance.mw.*;

public class RemittanceMWRequestFixture {
    public static GeneralParametersMWRequest withDefaultGeneralParameters() {
        return GeneralParametersMWRequest.builder()
                .personId("161616")
                .codLanguage(1)
                .applicationId("1")
                .build();
    }

    public static ValidateAccountMWRequest withDefaultValidateAccount() {
        return ValidateAccountMWRequest.builder()
                .personId("161616")
                .jtsOidAccount("123456")
                .codLanguage(1)
                .applicationId(1)
                .build();
    }

    public static MoneyOrderSentMWRequest withDefaultMoneyOrdersSent() {
        return MoneyOrderSentMWRequest.builder()
                .personId("161616")
                .applicationId("1")
                .build();
    }

    public static CheckRemittanceMWRequest withDefaultCheckRemittance() {
        return CheckRemittanceMWRequest.builder()
                .personId("161616")
                .applicationId("1")
                .withGanaMobile("1")
                .noRemittance("123456789")
                .build();
    }

    public static DepositRemittanceMWRequest withDefaultDepositRemittance() {
        return DepositRemittanceMWRequest.builder()
                .codPerson("161616")
                .codApplication("1")
                .remittanceNumber("123456789")
                .queryNumber("123456789")
                .jtsOidAccount("123456")
                .build();
    }


    public static UpdateWURemittanceMWRequest withDefaultUpdateWURemittance(){
        return UpdateWURemittanceMWRequest.builder()
                .personId("4766171")
                .applicationId("2")
                .noConsult("5000")
                .relation("Padre")
                .origin("Genaro")
                .transaction("911")
                .company("BANCO SOL")
                .companyLevel("7")
                .entryDate("2025-05-05")
                .laborType("Doctor")
                .build();
    }

    public static ConsultWURemittanceMWRequest withDefaultConsultWURemittance(){
        return ConsultWURemittanceMWRequest.builder()
                .personId("252525")
                .applicationId("2")
                .noRemittance("147147")
                .jtsOidAccount("123123123")
                .build();
    }
}
