package bg.com.bo.bff.providers.dtos.response.third.account.mw;

import bg.com.bo.bff.providers.dtos.response.ach.account.mw.AddAccountMWResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;

import java.util.Collections;

public class ThirdAccountMWResponseFixture {
    public static ApiDataResponse<AddAccountMWResponse> withDefaultAddAccountMWResponse() {
        return ApiDataResponse.of(new AddAccountMWResponse("123"));
    }

    public static ThirdAccountsMWResponse withDefaultThirdAccountListMWResponse() {
        return ThirdAccountsMWResponse.builder()
                .data(Collections.singletonList(
                        ThirdAccountsMWResponse.ThirdAccountMW.builder()
                                .id("1")
                                .accountId("123456789")
                                .accountNumber("987654321")
                                .currencyCode("USD")
                                .currencyAcronym("$")
                                .accountType("Saving")
                                .accountTypeAbbreviation("SAV")
                                .clientName("John Doe")
                                .accountAliases("Personal Account")
                                .isFavorite("true")
                                .build()
                ))
                .build();
    }
}
