package bg.com.bo.bff.providers.dtos.response.remittance;

import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.CheckRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.DepositRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPCurrenciesMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPEconomicActivitiesMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPExtensionsMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPIncomeSourcesMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPJobLevelsMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPRelationshipsMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.GPTransactionReasonsMW;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.UpdateWURemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ValidateAccountMWResponse;
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
                        .senderNumberId("129004")
                        .mtcn("8037118303")
                        .nameTypeReceiver("D")
                        .firstNameReceiver("ROBERT")
                        .lastNameReceiver("MOORE")
                        .givenNameReceiver("")
                        .paternalNameReceiver("")
                        .maternalNameReceiver("")
                        .build()))
                .build();
    }

    public static CheckRemittanceMWResponse withDefaultCheckRemittance() {
        return CheckRemittanceMWResponse.builder()
                .data(Collections.singletonList(CheckRemittanceMWResponse.CheckRemittanceMW.builder()
                        .noRemittance("123456789")
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

    public static DepositRemittanceMWResponse withDefaultDepositRemittance() {
        return DepositRemittanceMWResponse.builder()
                .data(Collections.singletonList(DepositRemittanceMWResponse.DepositRemittanceMW.builder()
                        .codRemittance("0000")
                        .accountingEntry(123456789)
                        .startTime("2021-05-11T04:00:00.000+00:00")
                        .description("Test")
                        .nameRemittance("Test")
                        .amountReceived(BigDecimal.valueOf(1000.0))
                        .currencyReceived("0")
                        .exchangeRate(BigDecimal.valueOf(1.0))
                        .commission(BigDecimal.valueOf(0.0))
                        .creditedAmount(BigDecimal.valueOf(1000.0))
                        .build()))
                .build();
    }

    public static CheckRemittanceMWResponse withDefaultConsultWURemittance() {
        return CheckRemittanceMWResponse.builder()
                .data(Collections.singletonList(CheckRemittanceMWResponse.CheckRemittanceMW.builder()
                        .noRemittance("1000")
                        .noConsult(10)
                        .amountReceived(BigDecimal.valueOf(350.0))
                        .currencyReceived("10000")
                        .countryEmission("USA")
                        .plazaOrigin("PlazaOrigen")
                        .payer("Juan Perez")
                        .noTelephone("7111111")
                        .telephoneBeneficiary("50499999999")
                        .countryDestination("Bolivia")
                        .plazaDestination("PlazaDestino")
                        .beneficiary("Arnoldo Gonzales")
                        .noDocument("353535")
                        .documentType("CI")
                        .extension("SC")
                        .build())).build();
    }

    public static UpdateWURemittanceMWResponse withDefaultUpdateWURemittance() {
        return UpdateWURemittanceMWResponse.builder()
                .data(UpdateWURemittanceMWResponse.UpdateWURemittanceMW.builder()
                        .codeError("1000")
                        .company("BANCO GANADERO")
                        .companyLevel("7")
                        .entryDate("2025-04-01")
                        .laborType("Arquitecto")
                        .pcc01("Datos actualizados correctamente.").build()).build();
    }
}
