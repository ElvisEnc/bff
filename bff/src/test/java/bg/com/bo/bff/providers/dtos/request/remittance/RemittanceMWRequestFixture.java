package bg.com.bo.bff.providers.dtos.request.remittance;

import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ValidateAccountMWRequest;

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
}
