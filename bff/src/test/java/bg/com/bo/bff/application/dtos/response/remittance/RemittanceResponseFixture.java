package bg.com.bo.bff.application.dtos.response.remittance;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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

    public static List<MoneyOrderSentResponse> withDataDefaultListMoneyOrderSentResponse() {
        return Collections.singletonList(MoneyOrderSentResponse.builder()
                .orderId("129004")
                .transactionId("8037118303")
                .transactionType("D")
                .fromHolderName("ROBERT")
                .fromLastName("MOORE")
                .toHolderName("")
                .toPaternalLastName("")
                .toMaternalLastName("")
                .build());
    }
}