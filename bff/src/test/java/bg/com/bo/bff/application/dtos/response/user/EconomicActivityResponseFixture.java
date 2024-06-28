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

    public static EconomicActivityResponse withDefaultEconomicActivity() {
        return EconomicActivityResponse.builder()
                .economicActivity(Arrays.asList(EconomicalActivityFixture.economicActivity()))
                .incomeLevel(Arrays.asList(EconomicalActivityFixture.incomeLevel()))
                .incomeSource(Arrays.asList(EconomicalActivityFixture.incomeSource()))
                .jobTitle(Arrays.asList(EconomicalActivityFixture.jobTitle()))
                .build();
    }
}