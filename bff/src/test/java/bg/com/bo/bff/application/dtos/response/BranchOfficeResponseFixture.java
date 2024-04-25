package bg.com.bo.bff.application.dtos.response;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BranchOfficeResponseFixture {
    public static BranchOfficeResponse withDefault() {
        return BranchOfficeResponse.builder()
                .data(Arrays.asList(
                        BranchOfficeDataResponse.builder()
                                .id("001")
                                .description("Sucursal 1")
                                .build(),
                        BranchOfficeDataResponse.builder()
                                .id("002")
                                .description("Sucursal 2")
                                .build()
                ))
                .build();
    }
}