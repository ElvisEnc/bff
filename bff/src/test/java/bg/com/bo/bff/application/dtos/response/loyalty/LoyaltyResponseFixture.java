package bg.com.bo.bff.application.dtos.response.loyalty;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;

public class LoyaltyResponseFixture {

    public static LoyaltySystemCodeResponse withDefaultSystemCode() {
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(1)
                .build();
    }
    public static LoyaltySumPointResponse withDefaultSumPoint() {
        return LoyaltySumPointResponse.builder()
                .points(1)
                .build();
    }

    public static GenericResponse withDefaultGeneric() {
        return GenericResponse.builder()
                .code("OK")
                .message("OK")
                .title("OK")
                .build();
    }
}
