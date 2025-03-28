package bg.com.bo.bff.mappings.providers.remittance;

import bg.com.bo.bff.application.dtos.request.remittance.UpdateWURemittanceRequest;
import bg.com.bo.bff.application.dtos.response.remittance.UpdateWURemittanceResponse;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.UpdateWURemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.UpdateWURemittanceMWResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;

import bg.com.bo.bff.application.dtos.request.remittance.ConsultWURemittanceRequest;
import bg.com.bo.bff.application.dtos.request.remittance.DepositRemittanceRequest;
import bg.com.bo.bff.application.dtos.response.remittance.CheckRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.DepositRemittanceResponse;
import bg.com.bo.bff.application.dtos.response.remittance.GPCurrencies;
import bg.com.bo.bff.application.dtos.response.remittance.GPEconomicActivities;
import bg.com.bo.bff.application.dtos.response.remittance.GPExtensions;
import bg.com.bo.bff.application.dtos.response.remittance.GPIncomeSources;
import bg.com.bo.bff.application.dtos.response.remittance.GPJobLevels;
import bg.com.bo.bff.application.dtos.response.remittance.GPRelationships;
import bg.com.bo.bff.application.dtos.response.remittance.GPTransactionReasons;
import bg.com.bo.bff.application.dtos.response.remittance.ListGeneralParametersResponse;
import bg.com.bo.bff.application.dtos.response.remittance.MoneyOrderSentResponse;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.CheckRemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ConsultWURemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.DepositRemittanceMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.GeneralParametersMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.MoneyOrderSentMWRequest;
import bg.com.bo.bff.providers.dtos.request.remittance.mw.ValidateAccountMWRequest;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.CheckRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.DepositRemittanceMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.ListGeneralParametersMWResponse;
import bg.com.bo.bff.providers.dtos.response.remittance.mw.MoneyOrderSentMWResponse;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareEnums;


@Component
public class RemittanceMapper implements IRemittanceMapper {

    @Override
    public GeneralParametersMWRequest mapperRequest(String personId) {
        return GeneralParametersMWRequest.builder()
                .codLanguage(Integer.parseInt(RemittanceMiddlewareEnums.CODE_LANGUAGE.getCode()))
                .personId(personId)
                .applicationId(CanalMW.GANAMOVIL.getCanal())
                .build();
    }

    @Override
    public ValidateAccountMWRequest mapperRequest(String personId, String accountId) {
        return ValidateAccountMWRequest.builder()
                .codLanguage(Integer.parseInt(RemittanceMiddlewareEnums.CODE_LANGUAGE.getCode()))
                .personId(personId)
                .applicationId(Integer.parseInt(CanalMW.GANAMOVIL.getCanal()))
                .jtsOidAccount(accountId)
                .build();
    }

    @Override
    public MoneyOrderSentMWRequest mapperRequestOrders(String personId) {
        return MoneyOrderSentMWRequest.builder()
                .personId(personId)
                .applicationId(CanalMW.GANAMOVIL.getCanal())
                .build();
    }

    @Override
    public CheckRemittanceMWRequest mapperRequestRemittance(String personId, String remittanceId) {
        return CheckRemittanceMWRequest.builder()
                .personId(personId)
                .applicationId(CanalMW.GANAMOVIL.getCanal())
                .withGanaMobile(RemittanceMiddlewareEnums.GANAMOVIL.getCode())
                .noRemittance(remittanceId)
                .build();
    }

    @Override
    public DepositRemittanceMWRequest mapperRequestDeposit(String personId, String remittanceId, DepositRemittanceRequest request) {
        return DepositRemittanceMWRequest.builder()
                .codPerson(personId)
                .codApplication(CanalMW.GANAMOVIL.getCanal())
                .remittanceNumber(remittanceId)
                .queryNumber(request.getConsultationId())
                .jtsOidAccount(request.getAccountId())
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
                        .orderId(mw.getSenderNumberId())
                        .transactionId(mw.getMtcn())
                        .transactionType(mw.getNameTypeReceiver())
                        .fromHolderName(mw.getFirstNameReceiver().trim())
                        .fromLastName(mw.getLastNameReceiver().trim())
                        .toHolderName(mw.getGivenNameReceiver().trim())
                        .toPaternalLastName(mw.getPaternalNameReceiver().trim())
                        .toMaternalLastName(mw.getMaternalNameReceiver().trim())
                        .build())
                .toList();
    }

    @Override
    public List<CheckRemittanceResponse> convertResponse(CheckRemittanceMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> CheckRemittanceResponse.builder()
                        .remittanceId(mw.getNoRemittance())
                        .consultationId(mw.getNoConsult())
                        .amount(mw.getAmountReceived())
                        .currencyCode(Util.convertCurrency(mw.getCurrencyReceived()))
                        .originCountry(mw.getCountryEmission())
                        .originCity(mw.getPlazaOrigin())
                        .holderName(mw.getPayer())
                        .phone(mw.getNoTelephone())
                        .recipientPhone(mw.getTelephoneBeneficiary())
                        .recipientCountry(mw.getCountryDestination())
                        .recipientCity(mw.getPlazaDestination())
                        .recipientName(mw.getBeneficiary())
                        .documentNumber(mw.getNoDocument())
                        .documentType(mw.getDocumentType())
                        .documentExtension(mw.getExtension())
                        .build()
                ).toList();
    }

    @Override
    public List<DepositRemittanceResponse> convertResponse(DepositRemittanceMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> DepositRemittanceResponse.builder()
                        .remittanceId(mw.getRemittanceCode())
                        .accountingEntry(mw.getAccountingEntry())
                        .time(mw.getStartTime())
                        .description(mw.getDescription())
                        .remittanceName(mw.getRemittanceName())
                        .amountReceived(mw.getReceivedAmount())
                        .currencyCode(Util.convertCurrency(mw.getReceivedCurrency()))
                        .exchangeRate(mw.getExchangeRate())
                        .commission(mw.getCommission())
                        .amountPaid(mw.getCreditedAmount())
                        .build()
                ).toList();
    }

    @Override
    public ConsultWURemittanceMWRequest mapperRequestRemittanceWU(String personId, String remittanceId,
                                                                  ConsultWURemittanceRequest request) {
        return ConsultWURemittanceMWRequest.builder()
                .personId(personId)
                .applicationId(CanalMW.GANAMOVIL.getCanal())
                .noRemittance(remittanceId)
                .jtsOidAccount(request.getJtsOidAccount())
                .build();
    }

    @Override
    public UpdateWURemittanceMWRequest mapperRequestRemittanceWUUpdate(String personId, String consultId,
                                                                       UpdateWURemittanceRequest request) {
        return UpdateWURemittanceMWRequest.builder()
                .personId(personId)
                .applicationId(CanalMW.GANAMOVIL.getCanal())
                .noConsult(consultId)
                .relation(request.getRelation())
                .origin(request.getOrigin())
                .transaction(request.getTransaction())
                .company(request.getCompany())
                .companyLevel(request.getCompanyLevel())
                .entryDate(request.getEntryDate())
                .laborType(request.getLaborType())
                .build();
    }

    @Override
    public UpdateWURemittanceResponse convertResponse(UpdateWURemittanceMWResponse mwResponse) {

        return UpdateWURemittanceResponse.builder()
                .codeError(mwResponse.getData().getCodeError())
                .company(mwResponse.getData().getCompany())
                .companyLevel(mwResponse.getData().getCompanyLevel())
                .entryDate(mwResponse.getData().getEntryDate())
                .laborType(mwResponse.getData().getLaborType())
                .pcc01(mwResponse.getData().getPcc01())
                .build();

    }

}
