package bg.com.bo.bff.application.dtos.response.user;

import java.util.Arrays;

public class MaritalStatusResponseFixture {
    public static MaritalStatusResponse withDefault() {
        return MaritalStatusResponse.builder()
                .data(Arrays.asList(MaritalStatusFixture.withDefault(), MaritalStatusFixture.withDefault()))
                .build();
    }
}