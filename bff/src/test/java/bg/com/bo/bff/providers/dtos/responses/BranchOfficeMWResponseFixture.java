package bg.com.bo.bff.providers.dtos.responses;

import java.util.Arrays;

public class BranchOfficeMWResponseFixture {
    public static BranchOfficeMWResponse withDefault() {
        return BranchOfficeMWResponse.builder()
                .data(BranchOfficeMWResponse.BranchOfficeMWData.builder()
                        .response(Arrays.asList(
                                BranchOfficeMWResponse.BranchOfficeMWData.BranchOfficeArray.builder()
                                        .description("Sucursal 1")
                                        .branchCode("0001")
                                        .entityCode("E001")
                                        .build(),
                                BranchOfficeMWResponse.BranchOfficeMWData.BranchOfficeArray.builder()
                                        .description("Sucursal 2")
                                        .branchCode("0002")
                                        .entityCode("E002")
                                        .build()
                        ))
                        .build())
                .build();
    }
}