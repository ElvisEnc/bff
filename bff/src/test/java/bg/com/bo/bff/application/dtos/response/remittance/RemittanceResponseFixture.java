package bg.com.bo.bff.application.dtos.response.remittance;

import java.math.BigDecimal;
import java.util.Collections;

public class RemittanceResponseFixture {
    public static ListGeneralParametersResponse withDefaultGeneralParameters() {
        return ListGeneralParametersResponse.builder()
                .currencies(Collections.singletonList(GPCurrencies.builder()
                        .currencyId(1)
                        .abbreviation("USD")
                        .purchaseRate(BigDecimal.valueOf(1.0))
                        .description("US Dollar")
                        .currencyType('1')
                        .build()
                ))
                .extensions(Collections.singletonList(GPExtensions.builder()
                        .extensionId(1)
                        .name("Test")
                        .abbreviation("Test")
                        .build()
                ))
                .transactionReasons(Collections.singletonList(GPTransactionReasons.builder()
                        .transactionReasonId(1)
                        .description("Test")
                        .build()
                ))
                .relationships(Collections.singletonList(GPRelationships.builder()
                        .relationshipId(1)
                        .description("Test")
                        .build()
                ))
                .incomeSources(Collections.singletonList(GPIncomeSources.builder()
                        .incomeSourceId(1)
                        .description("Test")
                        .build()
                ))
                .jobLevels(Collections.singletonList(GPJobLevels.builder()
                        .jobLevelId(1)
                        .description("Test")
                        .build()
                ))
                .economicActivities(Collections.singletonList(GPEconomicActivities.builder()
                        .name("Test")
                        .incomeSourceCode('A')
                        .startDate("01/01/2024")
                        .build()
                ))
                .build();
    }
}