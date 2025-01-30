package bg.com.bo.bff.providers.dtos.response.remittance;

import bg.com.bo.bff.providers.dtos.response.remittance.mw.*;

import java.math.BigDecimal;
import java.util.Collections;

public class RemittanceMWResponseFixture {
    public static ListGeneralParametersMWResponse withDefaultGeneralParameters() {
        return ListGeneralParametersMWResponse.builder()
                .currencies(Collections.singletonList(GPCurrenciesMW.builder()
                        .currencyCode(1)
                        .currencyAbbr("USD")
                        .commonPurchaseRate(BigDecimal.valueOf(1.0))
                        .currencyDescription("US Dollar")
                        .currencyType('1')
                        .build()
                ))
                .extensions(Collections.singletonList(GPExtensionsMW.builder()
                        .extensionId(1)
                        .extensionName("Test")
                        .extensionAbbr("Test")
                        .build()
                ))
                .transactionReasons(Collections.singletonList(GPTransactionReasonsMW.builder()
                        .transactionReasonId(1)
                        .transactionReasonDescription("Test")
                        .build()
                ))
                .relationships(Collections.singletonList(GPRelationshipsMW.builder()
                        .relationshipId(1)
                        .relationshipDescription("Test")
                        .build()
                ))
                .incomeSources(Collections.singletonList(GPIncomeSourcesMW.builder()
                        .incomeSourceId(1)
                        .incomeSourceDescription("Test")
                        .build()
                ))
                .jobLevels(Collections.singletonList(GPJobLevelsMW.builder()
                        .jobLevelId(1)
                        .jobLevelDescription("Test")
                        .build()
                ))
                .economicActivities(Collections.singletonList(GPEconomicActivitiesMW.builder()
                        .companyName("Test")
                        .incomeSourceCode('A')
                        .startDate("2015-05-11T04:00:00.000+00:00")
                        .build()
                ))
                .build();
    }

    public static ValidateAccountMWResponse withDefaultValidateAccount() {
        return ValidateAccountMWResponse.builder()
                .codeError("COD000")
                .message("SIN BLOQUEO")
                .build();
    }

    public static MoneyOrderSentMWResponse withDefaultMoneyOrdersSent() {
        return MoneyOrderSentMWResponse.builder()
                .data(Collections.singletonList(MoneyOrderSentMWResponse.MoneyOrderSentMW.builder()
                        .idNumber("129004")
                        .mtcn("8037118303")
                        .rNameTypeReceiver("D")
                        .rFirstNameReceiver("ROBERT")
                        .rLastNameReceiver("MOORE")
                        .rGivenNameReceiver("")
                        .rPaternalNameReceiver("")
                        .rMaternalNameReceiver("")
                        .build()))
                .build();
    }
}
