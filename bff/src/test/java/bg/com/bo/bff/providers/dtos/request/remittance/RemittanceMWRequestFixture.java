package bg.com.bo.bff.providers.dtos.request.remittance;

import bg.com.bo.bff.providers.dtos.request.remittance.mw.*;

public class RemittanceMWRequestFixture {
    public static GeneralParametersMWRequest withDefaultGeneralParameters() {
        return GeneralParametersMWRequest.builder()
                .codPerson("161616")
                .codLanguage(1)
                .codApplication("1")
                .build();
    }

    public static ValidateAccountMWRequest withDefaultValidateAccount() {
        return ValidateAccountMWRequest.builder()
                .codPerson("161616")
                .jtsOidAccount("123456")
                .codLanguage(1)
                .codApplication(1)
                .build();
    }

    public static MoneyOrderSentMWRequest withDefaultMoneyOrdersSent() {
        return MoneyOrderSentMWRequest.builder()
                .codPerson("161616")
                .codLanguage(1)
                .codApplication("1")
                .build();
    }

    public static CheckRemittanceMWRequest withDefaultCheckRemittance() {
        return CheckRemittanceMWRequest.builder()
                .codPerson("161616")
                .codApplication("1")
                .withGanaMobile("1")
                .noRemittance("123456789")
                .build();
    }

    public static DepositRemittanceMWRequest withDefaultDepositRemittance() {
        return DepositRemittanceMWRequest.builder()
                .personId("161616")
                .applicationId("1")
                .noRemittance("123456789")
                .noConsult("123456789")
                .jtsOidAccount("123456")
                .build();
    }

    public static DepositRemittanceWUMWRequest withDefaultDepositRemittanceWU() {
        return DepositRemittanceWUMWRequest.builder()
                .personId("161616")
                .applicationId("1")
                .noRemittance("123456789")
                .noConsult("123456789")
                .jtsOidAccount("123456")
                .pCType("0")
                .originFund("1")
                .originDestination("1")
                .build();
    }
}
