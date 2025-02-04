package bg.com.bo.bff.mappings.providers.services;

import bg.com.bo.bff.application.dtos.request.payment.service.AffiliationDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.ValidateAffiliateCriteriaRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.PaymentDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.DataRegisterServiceAffiliation;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.DataServiceAffiliation;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.DependencyServiceAffiliation;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DeleteAffiliateServiceMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.ValidateAffiliateCriteriaMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentDebtsMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DataRegisterServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DataServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DependencyServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PaymentServicesMapper implements IPaymentServicesMapper {
    @Override
    public SubcategoriesResponse convertResponse(SubcategoriesMWResponse response) {
        return new SubcategoriesResponse(
                response.getData()
                        .stream()
                        .map(x -> new SubcategoriesResponse.Subcategory(x.getSubCategoryCod(), x.getCategoryCod(), x.getDescription(), Objects.equals(x.getHasCity(), "S")))
                        .toList());
    }

    @Override
    public List<CategoryResponse> convertResponse(CategoryMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> CategoryResponse.builder()
                        .categoryId(mw.getIdCategory())
                        .categoryName(mw.getDescription())
                        .categoryDescription(mw.getDetail())
                        .build())
                .toList();
    }

    @Override
    public SubCategoryCitiesResponse convertResponse(SubCategoryCitiesMWResponse response) {
        List<SubCategoryCitiesResponse.City> data = response.getData().stream().map(x -> new SubCategoryCitiesResponse.City(x.getId(), x.name)).toList();
        return new SubCategoryCitiesResponse(data);
    }

    @Override
    public List<AffiliatedService> convertResponse(AffiliatedServiceMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> AffiliatedService.builder()
                        .categoryId(mw.getIdCategory())
                        .subCategoryId(mw.getIdSubCategory())
                        .subCategoryName(mw.getSubCategoryDesc())
                        .affiliateServiceId(mw.getAffiliationCode())
                        .serviceCode(mw.getServiceCode())
                        .serviceName(mw.getServiceDesc())
                        .internalCode(mw.getInternalCod())
                        .referenceName(mw.getReferenceName())
                        .nameHolder(mw.getNameHolder())
                        .year(mw.getYear())
                        .contingency(!mw.getStateContingency().equals("N"))
                        .build())
                .toList();
    }

    @Override
    public List<AffiliatedServicesResponse> convertResponse(List<AffiliatedService> response) {
        Map<String, Map<String, List<AffiliatedService>>> groupedMap = response.stream()
                .collect(Collectors.groupingBy(
                        AffiliatedService::getServiceCode,
                        Collectors.groupingBy(AffiliatedService::getServiceName)
                ));

        return (groupedMap.entrySet().stream()
                .flatMap(serviceCodeEntry -> serviceCodeEntry.getValue().entrySet().stream()
                        .map(serviceNameEntry -> {
                            AffiliatedServicesResponse groupedData = new AffiliatedServicesResponse();
                            groupedData.setServiceCode(serviceCodeEntry.getKey());
                            groupedData.setServiceName(serviceNameEntry.getKey());
                            groupedData.setData(serviceNameEntry.getValue());
                            groupedData.setTotal(serviceNameEntry.getValue().size());

                            if (!serviceNameEntry.getValue().isEmpty()) {
                                AffiliatedService firstService = serviceNameEntry.getValue().get(0);
                                groupedData.setCategoryId(firstService.getCategoryId());
                                groupedData.setSubCategoryId(firstService.getSubCategoryId());
                                groupedData.setSubCategoryName(firstService.getSubCategoryName());
                            }

                            return groupedData;
                        })
                )
                .sorted(Comparator.comparing(AffiliatedServicesResponse::getSubCategoryName))
                .toList());
    }

    @Override
    public DebtsConsultationMWRequest mapperRequest(Integer personId, Integer affiliateServiceId, AffiliationDebtsRequest request) {
        return new DebtsConsultationMWRequest(request.serviceCode(), personId, request.year(), affiliateServiceId);
    }

    @Override
    public PaymentDebtsMWRequest mapperRequest(String personId, String affiliateServiceId, PaymentDebtsRequest request) {
        return PaymentDebtsMWRequest.builder()
                .ownerAccount(PaymentDebtsMWRequest.PaymentOwnerAccount.builder()
                        .schemeName("personId")
                        .personId(personId)
                        .build()
                )
                .instructedAmount(PaymentDebtsMWRequest.PaymentAmount.builder()
                        .currency(request.currencyCode())
                        .amount(request.amount())
                        .build())
                .debtorAccount(PaymentDebtsMWRequest.PaymentDebtor.builder()
                        .schemeName("AccountId")
                        .identification(String.valueOf(request.fromAccountId()))
                        .build())
                .supplementaryData(PaymentDebtsMWRequest.PaymentSupplementary.builder()
                        .idGeneratedForDebt(request.idGenerated())
                        .invoiceNITCI(request.invoiceNit())
                        .invoiceName(request.invoiceName())
                        .company(request.company())
                        .affiliationCode(affiliateServiceId)
                        .serviceCode(request.serviceCode())
                        .description(request.description())
                        .build())
                .risk(PaymentDebtsMWRequest.PaymentRisk.builder()
                        .paymentContextCode("PaymentService")
                        .build())
                .build();
    }

    @Override
    public ServiceAffiliationMWRequest mapperRequest(String personId, ServiceAffiliationRequest request) {
        return ServiceAffiliationMWRequest.builder()
                .serviceCode(request.serviceCode())
                .criteriaSearchId(request.criteriaId())
                .referenceName(request.referenceName())
                .year(request.year())
                .personId(personId)
                .accountNumber(request.accountNumber())
                .isTemporal(request.isTemporal() ? "S" : "N")
                .searchFields(mapDependencyServiceAffiliations(request.searchFields()))
                .dataAffiliation(mapDataServiceAffiliations(request.dataAffiliation()))
                .build();
    }

    private List<DependencyServiceAffiliationMW> mapDependencyServiceAffiliations(List<DependencyServiceAffiliation> list) {
        return list.stream()
                .map(dep -> new DependencyServiceAffiliationMW(dep.code(), dep.value()))
                .toList();
    }

    private List<DataServiceAffiliationMW> mapDataServiceAffiliations(List<DataServiceAffiliation> dataAffiliation) {
        return dataAffiliation.stream()
                .map(da -> new DataServiceAffiliationMW(
                        da.identify(),
                        da.nameOwner(),
                        da.code(),
                        da.description(),
                        da.additionalFact(),
                        mapDataRegisterServiceAffiliations(da.dataRegister())
                ))
                .toList();
    }

    private List<DataRegisterServiceAffiliationMW> mapDataRegisterServiceAffiliations(List<DataRegisterServiceAffiliation> dataRegister) {
        return dataRegister.stream()
                .map(dr -> new DataRegisterServiceAffiliationMW(
                        dr.label(),
                        dr.value(),
                        dr.mandatory(),
                        dr.edit(),
                        dr.group(),
                        dr.description(),
                        dr.code()
                ))
                .toList();
    }

    @Override
    public AffiliationDebtsResponse convertDebtsResponse(DebtsConsultationMWResponse mwResponse) {
        List<DebtDetail> debtDetails = mwResponse.getData().getDebtDetails().stream()
                .map(detail ->
                        DebtDetail.builder()
                                .description(detail.getDescription())
                                .referenceCode(detail.getReferenceCode())
                                .monthPeriod(detail.getMonthPeriod())
                                .yearPeriod(detail.getYear())
                                .commissionAmount(detail.getCommissionAmount())
                                .currencyCode(detail.getCurrencyCode())
                                .amount(detail.getAmount())
                                .accumulatedAmount(detail.getAccumulatedAmount())
                                .identifier(detail.getIdentifier())
                                .validationType(detail.getValidationType())
                                .detail(detail.getDetail())
                                .additionalDataDetails(detail.getAdditionalDataDetails())
                                .paymentPlan(detail.getPaymentPlanCode())
                                .idGenerated(detail.getIdGeneratedForDebt())
                                .build())
                .toList();
        return AffiliationDebtsResponse.builder()
                .affiliateServiceId(mwResponse.getData().getAffiliationCode())
                .serviceCode(mwResponse.getData().getServiceCode())
                .invoiceNit(mwResponse.getData().getInvoiceTaxId())
                .invoiceName(mwResponse.getData().getInvoiceName())
                .invoiceCanModify(mwResponse.getData().getInvoiceCanModifyData() != null && !"N".equals(mwResponse.getData().getInvoiceCanModifyData()))
                .debtDetails(debtDetails)
                .build();
    }

    @Override
    public PaymentDebtsResponse convertPaymentResponse(PaymentDebtsMWResponse mwResponse) {
        return PaymentDebtsResponse.builder()
                .status(mwResponse.status())
                .maeId(mwResponse.maeId())
                .nroTransaction(mwResponse.nroTransaction())
                .receiptDetail(PaymentDebtsDetail.builder()
                        .affiliationNumber(mwResponse.receiptDetail().affiliationNumber())
                        .servicePaymentCode(mwResponse.receiptDetail().servicePaymentCode())
                        .company(mwResponse.receiptDetail().company())
                        .accountingDate(UtilDate.formatDate(mwResponse.receiptDetail().accountingDate()))
                        .accountingTime(Util.formatterTime(mwResponse.receiptDetail().accountingTime()))
                        .accountingEntry(mwResponse.receiptDetail().accountingEntry())
                        .currency(mwResponse.receiptDetail().currency())
                        .amount(mwResponse.receiptDetail().amount())
                        .description(mwResponse.receiptDetail().description())
                        .fromAccountNumber(UtilDate.formatDate(mwResponse.receiptDetail().fromAccountNumber()))
                        .fromHolder(mwResponse.receiptDetail().fromHolder())
                        .exchangeAmount(mwResponse.receiptDetail().exchangeAmount())
                        .fromAccountCurrency(mwResponse.receiptDetail().fromAccountCurrency())
                        .exchangeRateDebit(mwResponse.receiptDetail().exchangeRateDebit())
                        .build())
                .build();
    }

    @Override
    public ServiceAffiliationResponse convertServiceAffiliationResponse(ServiceAffiliationMWResponse mwRequest) {
        return new ServiceAffiliationResponse(mwRequest.affiliationNewCod());
    }

    @Override
    public List<ServiceResponse> convertResponse(ListServicesMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> ServiceResponse.builder()
                        .serviceCode(mw.getServiceCode())
                        .serviceName(mw.getServiceName())
                        .categoryId(mw.getIdCategory())
                        .subCategoryId(mw.getIdSubCategory())
                        .build())
                .toList();
    }

    @Override
    public DeleteAffiliateServiceMWRequest convertRequest(String personId, String affiliateServiceId) {
        return DeleteAffiliateServiceMWRequest.builder()
                .affiliationCode(affiliateServiceId)
                .personId(personId)
                .build();

    }

    @Override
    public AffiliateCriteriaResponse convertResponse(AffiliateCriteriaMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return AffiliateCriteriaResponse.builder()
                    .year(0)
                    .serviceCode(null)
                    .criteria(Collections.emptyList())
                    .subServices(Collections.emptyList())
                    .build();
        List<AffiliateCriteriaResponse.SearchCriteria> criteria = mwResponse.getData().getSearchCriteria().stream()
                .map(mw -> AffiliateCriteriaResponse.SearchCriteria.builder()
                        .description(mw.getDescription())
                        .labelCriteria(mw.getLabelCriteria())
                        .criteriaId(mw.getSearchCriteriaId())
                        .criteriaIdAbbreviation(mw.getSearchCriteriaIdAbbreviation())
                        .fields(mw.getFields().stream()
                                .map(f -> AffiliateCriteriaResponse.SearchCriteria.Field.builder()
                                        .identifier(f.getIdentifier())
                                        .label(f.getLabel())
                                        .description(f.getDescription())
                                        .abbreviation(f.getAbbreviation())
                                        .isMandatory(f.getIsMandatory())
                                        .minimumLength(f.getMinimumLength())
                                        .maximumLength(f.getMaximumLength())
                                        .dataTypeCode(f.getDataTypeCode())
                                        .values(f.getValues().stream()
                                                .map(v -> AffiliateCriteriaResponse.SearchCriteria.Field.Value.builder()
                                                        .code(v.getCode())
                                                        .description(v.getDescription())
                                                        .build())
                                                .toList())
                                        .build())
                                .toList())
                        .build())
                .toList();
        List<AffiliateCriteriaResponse.SubService> subServices = mwResponse.getData().getSubServices().stream()
                .map(mw -> AffiliateCriteriaResponse.SubService.builder()
                        .abbreviation(mw.getAbbreviation())
                        .criteriaLabel(mw.getLabelCriteria())
                        .build())
                .toList();
        return AffiliateCriteriaResponse.builder()
                .year(mwResponse.getData().getYear())
                .serviceCode(mwResponse.getData().getServiceCode())
                .criteria(criteria)
                .subServices(subServices)
                .build();
    }

    @Override
    public ValidateAffiliateCriteriaMWRequest mapperRequest(String personId, String serviceCode, ValidateAffiliateCriteriaRequest request) {
        return ValidateAffiliateCriteriaMWRequest.builder()
                .serviceCode(Integer.valueOf(serviceCode))
                .year(request.getYear())
                .searchCriteriaId(request.getCriteriaId())
                .searchCriteriaIdAbbreviation(request.getCriteriaIdAbbreviation())
                .personId(Integer.valueOf(personId))
                .searchFields(request.getSearchFields().stream()
                        .map(f -> ValidateAffiliateCriteriaMWRequest.SearchField.builder()
                                .code(f.getCode())
                                .value(f.getValue())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public ValidateAffiliateCriteriaResponse convertResponse(ValidateAffiliateCriteriaMWResponse mwResponse) {
        return ValidateAffiliateCriteriaResponse.builder()
                .serviceCode(mwResponse.getServiceCode())
                .dataAffiliation(mwResponse.getDataAffiliation())
                .build();
    }
}
