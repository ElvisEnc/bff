package bg.com.bo.bff.mappings.providers.remittance;

import bg.com.bo.bff.application.dtos.response.remittance.*;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.MoneyOrderSentMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ValidateAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareEnums;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RemittanceMapper implements IRemittanceMapper {

    @Override
    public GeneralParametersMWRequest mapperRequest(String personId) {
        return GeneralParametersMWRequest.builder()
                .codLanguage(Integer.parseInt(RemittanceMiddlewareEnums.CODE_LANGUAGE.getCode()))
                .codPerson(personId)
                .codApplication(CanalMW.GANAMOVIL.getCanal())
                .build();
    }

    @Override
    public ValidateAccountMWRequest mapperRequest(String personId, String accountId) {
        return ValidateAccountMWRequest.builder()
                .codLanguage(Integer.parseInt(RemittanceMiddlewareEnums.CODE_LANGUAGE.getCode()))
                .codPerson(personId)
                .codApplication(Integer.parseInt(CanalMW.GANAMOVIL.getCanal()))
                .jtsOidAccount(accountId)
                .build();
    }

    @Override
    public MoneyOrderSentMWRequest mapperRequestOrders(String personId) {
        return MoneyOrderSentMWRequest.builder()
                .codLanguage(Integer.parseInt(RemittanceMiddlewareEnums.CODE_LANGUAGE.getCode()))
                .codPerson(personId)
                .codApplication(CanalMW.GANAMOVIL.getCanal())
                .build();
    }

    @Override
    public ListGeneralParametersResponse convertResponse(ListGeneralParametersMWResponse mwResponse) {
        return ListGeneralParametersResponse.builder()
                .currencies(mwResponse.getCurrencies() != null
                        ? mwResponse.getCurrencies().stream()
                        .map(currency -> GPCurrencies.builder()
                                .currencyId(currency.getCurrencyCode())
                                .abbreviation(currency.getCurrencyAbbr())
                                .purchaseRate(currency.getCommonPurchaseRate())
                                .description(currency.getCurrencyDescription())
                                .currencyType(currency.getCurrencyType())
                                .build())
                        .toList()
                        : Collections.emptyList())
                .extensions(mwResponse.getExtensions() != null
                        ? mwResponse.getExtensions().stream()
                        .map(extension -> GPExtensions.builder()
                                .extensionId(extension.getExtensionId())
                                .name(extension.getExtensionName())
                                .abbreviation(extension.getExtensionAbbr())
                                .build())
                        .toList()
                        : Collections.emptyList())
                .transactionReasons(mwResponse.getTransactionReasons() != null
                        ? mwResponse.getTransactionReasons().stream()
                        .map(extension -> GPTransactionReasons.builder()
                                .transactionReasonId(extension.getTransactionReasonId())
                                .description(extension.getTransactionReasonDescription())
                                .build())
                        .toList()
                        : Collections.emptyList())
                .relationships(mwResponse.getRelationships() != null
                        ? mwResponse.getRelationships().stream()
                        .map(extension -> GPRelationships.builder()
                                .relationshipId(extension.getRelationshipId())
                                .description(extension.getRelationshipDescription())
                                .build())
                        .toList()
                        : Collections.emptyList())
                .incomeSources(mwResponse.getIncomeSources() != null
                        ? mwResponse.getIncomeSources().stream()
                        .map(extension -> GPIncomeSources.builder()
                                .incomeSourceId(extension.getIncomeSourceId())
                                .description(extension.getIncomeSourceDescription())
                                .build())
                        .toList()
                        : Collections.emptyList())
                .jobLevels(mwResponse.getJobLevels() != null
                        ? mwResponse.getJobLevels().stream()
                        .map(extension -> GPJobLevels.builder()
                                .jobLevelId(extension.getJobLevelId())
                                .description(extension.getJobLevelDescription())
                                .build())
                        .toList()
                        : Collections.emptyList())
                .economicActivities(mwResponse.getEconomicActivities() != null
                        ? mwResponse.getEconomicActivities().stream()
                        .map(extension -> GPEconomicActivities.builder()
                                .name(extension.getCompanyName())
                                .incomeSourceCode(extension.getIncomeSourceCode())
                                .startDate(UtilDate.formatDate(extension.getStartDate()))
                                .build())
                        .toList()
                        : Collections.emptyList())
                .build();
    }

    @Override
    public List<MoneyOrderSentResponse> convertResponse(MoneyOrderSentMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> MoneyOrderSentResponse.builder()
                        .orderId(mw.getIdNumber())
                        .transactionId(mw.getMtcn())
                        .transactionType(mw.getRNameTypeReceiver())
                        .fromHolderName(mw.getRFirstNameReceiver().trim())
                        .fromLastName(mw.getRLastNameReceiver().trim())
                        .toHolderName(mw.getRGivenNameReceiver().trim())
                        .toPaternalLastName(mw.getRPaternalNameReceiver().trim())
                        .toMaternalLastName(mw.getRMaternalNameReceiver().trim())
                        .build())
                .toList();
    }
}
