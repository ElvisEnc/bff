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

    public static List<CheckRemittanceResponse> withDataDefaultListCheckRemittanceResponse() {
        return Collections.singletonList(CheckRemittanceResponse.builder()
                        .remittanceId(1)
                        .consultationId(1)
                        .amount(BigDecimal.valueOf(100.0))
                        .currencyCode("068")
                        .originCountry("Honduras")
                        .originCity("Tegucigalpa")
                        .holderName("Juan Perez")
                        .phone("50499999999")
                        .recipientPhone("50499999999")
                        .recipientCountry("Bolivia")
                        .recipientCity("La Paz")
                        .recipientName("Arnoldo Gonzales")
                        .documentNumber("0801199900001")
                        .documentType("CI")
                        .documentExtension("SC")
                .build());
    }

    public static List<DepositRemittanceResponse> withDataDefaultListDepositRemittanceResponse() {
        return Collections.singletonList(DepositRemittanceResponse.builder()
                        .remittanceId(1)
                        .accountingEntry(1)
                        .time("14:00")
                        .description("Test")
                        .remittanceName("Juan Perez")
                        .amountReceived(BigDecimal.valueOf(100.0))
                        .currencyCode("068")
                        .exchangeRate(BigDecimal.valueOf(1.0))
                        .commission(BigDecimal.valueOf(0.0))
                        .amountPaid(BigDecimal.valueOf(100.0))
                .build());
    }
}