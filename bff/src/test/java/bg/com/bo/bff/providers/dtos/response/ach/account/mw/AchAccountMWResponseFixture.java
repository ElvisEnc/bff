package bg.com.bo.bff.providers.dtos.response.ach.account.mw;

import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;

import java.util.Arrays;
import java.util.List;

public class AchAccountMWResponseFixture {

    public static ApiDataResponse<AddAccountMWResponse> withDefaultAddAchAccountMWResponse() {
        return ApiDataResponse.of(new AddAccountMWResponse("123"));
    }


    public static BanksMWResponse withDefaultBanksMWResponse() {
        return new BanksMWResponse(
                List.of(
                        new BankMW("ADM TARJETAS DE CREDITO A.T.C", "01111", "ADM"),
                        new BankMW("BANCO BISA", "01112", "BISA")
                )
        );
    }

    public static AchAccountsMWResponse withDefaultAchAccountMWResponse() {
        return AchAccountsMWResponse.builder()
                .data(Arrays.asList(
                        AchAccountMW.builder()
                                .idList("1")
                                .personId("123")
                                .bankCode("123")
                                .bankName("UnitTest")
                                .branchCode("UnitTest")
                                .accountTypeCode("UnitTest")
                                .accountNumber("123")
                                .documentNumber("123")
                                .holderName("UnitTest")
                                .accountNickname("UnitTest")
                                .isFavorite("UnitTest")
                                .build()
                ))
                .build();
    }

    public static BranchOfficeMWResponse withDefaultBranchOfficeMWResponse() {
        return BranchOfficeMWResponse.builder()
                .data(Arrays.asList(
                        BranchOfficeMWResponse.BranchOfficeMW.builder()
                                .description("Sucursal 1")
                                .branchCode("0001")
                                .entityCode("E001")
                                .build(),
                        BranchOfficeMWResponse.BranchOfficeMW.builder()
                                .description("Sucursal 2")
                                .branchCode("0002")
                                .entityCode("E002")
                                .build()
                ))
                .build();
    }
}