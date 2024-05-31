package bg.com.bo.bff.application.dtos.response.user;

import java.util.Arrays;

public class EconomicActivityResponseFixture {
    public static EconomicActivityResponse withDefault() {
        return EconomicActivityResponse.builder()
                .economicActivity(Arrays.asList(EconomicalActivityFixture.withDefault()))
                .incomeLevel(Arrays.asList(EconomicalActivityFixture.withDefault()))
                .incomeSource(Arrays.asList(EconomicalActivityFixture.withDefault()))
                .jobTitle(Arrays.asList(EconomicalActivityFixture.withDefault()))
                .build();
    }
}