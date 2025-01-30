package bg.com.bo.bff.providers.dtos.response.remittance;

import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.*;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static CheckRemittanceMWResponse withDefaultCheckRemittance() {
        return CheckRemittanceMWResponse.builder()
                .data(Collections.singletonList(CheckRemittanceMWResponse.CheckRemittanceMW.builder()
                        .noRemittance(123456789)
                        .noConsult(123456789)
                        .amountReceived(BigDecimal.valueOf(1000.0))
                        .currencyReceived("0")
                        .countryEmission("PARAGUAY")
                        .plazaOrigin("ASUNCION")
                        .payer("ROBERT MOORE")
                        .noTelephone("123456789")
                        .telephoneBeneficiary("123456789")
                        .countryDestination("PARAGUAY")
                        .plazaDestination("ASUNCION")
                        .beneficiary("ROBERT MOORE")
                        .noDocument("123456789")
                        .documentType("CI")
                        .extension("SC")
                        .build()))
                .build();
    }

    public static ApiErrorResponse withErrorRM001() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(RemittanceMiddlewareError.RM001.getCodeMiddleware())
                .description(RemittanceMiddlewareError.RM001.getMessage())
                .build();
        list.add(errorDetailResponse);
        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }
}
