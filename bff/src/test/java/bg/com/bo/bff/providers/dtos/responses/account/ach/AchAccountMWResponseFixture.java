package bg.com.bo.bff.providers.dtos.responses.account.ach;

import java.util.Arrays;

public class AchAccountMWResponseFixture {
    public static AchAccountMWResponse withDefault() {
        return AchAccountMWResponse.builder()
                .data(Arrays.asList(
                        AchAccountMW.builder()
                                .idList("987987654")
                                .personId("UnitTest")
                                .bankCode("UnitTest")
                                .bankName("UnitTest")
                                .branchCode("UnitTest")
                                .accountTypeCode("UnitTest")
                                .accountNumber("UnitTest")
                                .documentNumber("UnitTest")
                                .holderName("UnitTest")
                                .accountNickname("UnitTest")
                                .isFavorite("UnitTest")
                                .build()
                ))
                .build();
    }
}