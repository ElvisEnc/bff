package bg.com.bo.bff.application.dtos.response.payment.service;

import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class PaymentServiceResponseFixture {
    public static AffiliatedService withDefaultAffiliateServiceDetailResponse() {
        return AffiliatedService.builder()
                .affiliateServiceId("123")
                .serviceCode("123")
                .serviceName("test")
                .internalCode("123")
                .referenceName("test")
                .nameHolder("test")
                .year("123")
                .contingency(true)
                .build();
    }
    public static AffiliatedServicesResponse withDefaultAffiliateServiceResponse() {
        return AffiliatedServicesResponse.builder()
                .serviceCode("123")
                .serviceName("test")
                .data(Collections.singletonList(withDefaultAffiliateServiceDetailResponse()))
                .total(1)
                .build();
    }

    public static ApiDataResponse<List<AffiliatedServicesResponse>> withDataDefaultListAffiliateServiceResponse() {
        return ApiDataResponse.of(Collections.singletonList(withDefaultAffiliateServiceResponse()));
    }

    public static AffiliatedServicesResponse withDefaultAffiliateServiceResponseContingencyTrue() {
        return AffiliatedServicesResponse.builder()
                .serviceCode("123")
                .serviceName("test")
                .data(Stream.of(withDefaultAffiliateServiceDetailResponse())
                        .peek(detail -> detail.setContingency(false))
                        .toList())
                .total(1)
                .build();
    }

    public static ApiDataResponse<List<AffiliatedServicesResponse>> withDataDefaultListAffiliateServiceResponseContingencyTrue() {
        return ApiDataResponse.of(Collections.singletonList(withDefaultAffiliateServiceResponseContingencyTrue()));
    }

    public static AffiliationDebtsResponse withDefaultDebtsResponse() {
        return AffiliationDebtsResponse.builder()
                .affiliateServiceId("123")
                .serviceCode("123")
                .invoiceNit("123")
                .invoiceName("123")
                .invoiceCanModify(false)
                .debtDetails(Collections.singletonList(withDefaultDebtsDetail()))
                .build();
    }

    public static AffiliationDebtsResponse withDefaultDebtsResponseModifyTrue() {
        return AffiliationDebtsResponse.builder()
                .affiliateServiceId("123")
                .serviceCode("123")
                .invoiceNit("123")
                .invoiceName("123")
                .invoiceCanModify(true)
                .debtDetails(Collections.singletonList(withDefaultDebtsDetail()))
                .build();
    }

    public static AffiliationDebtsResponse withDefaultDebtsResponseModifyNull() {
        return AffiliationDebtsResponse.builder()
                .affiliateServiceId("123")
                .serviceCode("123")
                .invoiceNit("123")
                .invoiceName("123")
                .invoiceCanModify(false)
                .debtDetails(Collections.singletonList(withDefaultDebtsDetail()))
                .build();
    }

    public static DebtDetail withDefaultDebtsDetail() {
        return DebtDetail.builder()
                .description("123")
                .referenceCode("123")
                .monthPeriod(123)
                .yearPeriod(123)
                .commissionAmount(BigDecimal.valueOf(10.00))
                .currencyCode("123")
                .amount(BigDecimal.valueOf(10.00))
                .accumulatedAmount(BigDecimal.valueOf(10.00))
                .identifier(123L)
                .validationType("123")
                .detail("123")
                .additionalDataDetails("123")
                .paymentPlan("123")
                .idGenerated("123")
                .build();
    }

    public static ServiceAffiliationResponse withDefaultServiceAffiliationResponse() {
        return new ServiceAffiliationResponse("1946919");
    }

    public static CategoryResponse withDefaultCategoryResponse() {
        return CategoryResponse.builder()
                .categoryId("1")
                .categoryName("category")
                .categoryDescription("1")
                .build();
    }

    public static ApiDataResponse<List<CategoryResponse>> withDefaultDataListCategoryResponse() {
        return ApiDataResponse.of(Collections.singletonList(withDefaultCategoryResponse()));
    }

    public static List<ServiceResponse> withDefaultListServicesResponse() {
        return Collections.singletonList(withDefaultServiceResponse());
    }

    public static ServiceResponse withDefaultServiceResponse() {
        return ServiceResponse.builder()
                .serviceCode("123")
                .serviceName("test")
                .build();
    }

    public static SubcategoriesResponse withDefaultSubcategoriesResponse() {
        return new SubcategoriesResponse(
                List.of(
                        SubcategoriesResponse.Subcategory.builder()
                                .subcategoryId(1)
                                .categoryId(2)
                                .subcategoryName("UNIVERSIDADES")
                                .hasCity(Boolean.FALSE)
                                .build(),
                        SubcategoriesResponse.Subcategory.builder()
                                .subcategoryId(1)
                                .categoryId(2)
                                .subcategoryName("COLEGIOS")
                                .hasCity(Boolean.TRUE)
                                .build(),
                        SubcategoriesResponse.Subcategory.builder()
                                .subcategoryId(1)
                                .categoryId(2)
                                .subcategoryName("INSTITUTOS")
                                .hasCity(Boolean.FALSE)
                                .build()
                )
        );
    }

    public static SubCategoryCitiesResponse withDefaultSubCategoryCitiesResponse() {
        List<SubCategoryCitiesResponse.City> data = List.of(
                new SubCategoryCitiesResponse.City(1, "COCHABAMBA"),
                new SubCategoryCitiesResponse.City(1, "SANTA CRUZ")
        );
        return SubCategoryCitiesResponse.builder()
                .data(data)
                .build();
    }

    public static PaymentDebtsResponse withDefaultPaymentDebtsResponse() {
        return PaymentDebtsResponse.builder()
                .status("success")
                .maeId("mae123")
                .nroTransaction("txn123")
                .receiptDetail(withDefaultPaymentDebtsDetail())
                .build();
    }

    public static PaymentDebtsDetail withDefaultPaymentDebtsDetail() {
        return PaymentDebtsDetail.builder()
                .affiliationNumber("aff123")
                .servicePaymentCode("svc123")
                .company("Test Company")
                .accountingDate("2024-07-11")
                .accountingTime("10:00")
                .accountingEntry("entry123")
                .currency("USD")
                .amount(BigDecimal.valueOf(100.00))
                .description("Test Payment")
                .fromAccountNumber("123456789")
                .fromHolder("John Doe")
                .exchangeAmount(BigDecimal.valueOf(100.00))
                .fromAccountCurrency("USD")
                .exchangeRateDebit("1.0")
                .build();
    }

    public static AffiliateCriteriaResponse withDefaultAffiliateCriteriaResponse() {
        return AffiliateCriteriaResponse.builder()
                .serviceCode("123")
                .year(2024)
                .subServices(List.of(
                        AffiliateCriteriaResponse.SubService.builder()
                                .criteriaLabel("test")
                                .abbreviation("test")
                                .build()
                ))
                .criteria(List.of(
                        AffiliateCriteriaResponse.SearchCriteria.builder()
                                .labelCriteria("test")
                                .description("test")
                                .criteriaId("123")
                                .criteriaIdAbbreviation("123")
                                .fields(List.of(
                                        AffiliateCriteriaResponse.SearchCriteria.Field.builder()
                                                .identifier(123)
                                                .label("test")
                                                .description("test")
                                                .abbreviation("test")
                                                .isMandatory("S")
                                                .minimumLength("1")
                                                .maximumLength("10")
                                                .dataTypeCode("1")
                                                .values(List.of(
                                                        AffiliateCriteriaResponse.SearchCriteria.Field.Value.builder()
                                                                .code("test")
                                                                .description("test")
                                                                .build()
                                                ))
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }

    public static AffiliateCriteriaResponse withDefaultAffiliateCriteriaResponseNull() {
        return AffiliateCriteriaResponse.builder()
                .serviceCode(null)
                .year(0)
                .subServices(new ArrayList<>())
                .criteria(new ArrayList<>())
                .build();
    }

    public static ValidateAffiliateCriteriaResponse withDefaultValidateAffiliateCriteriaResponse() {
        return ValidateAffiliateCriteriaResponse.builder()
                .serviceCode("123")
                .dataAffiliation(List.of(
                        ValidateAffiliateCriteriaResponse.DataAffiliation.builder()
                                .identify(123)
                                .nameOwner("test")
                                .code("123")
                                .description("test")
                                .additionalFact("")
                                .dataRegister(
                                        List.of(
                                                ValidateAffiliateCriteriaResponse.DataAffiliation.DataRegister.builder()
                                                        .label("test")
                                                        .value("test")
                                                        .mandatory("S")
                                                        .edit("S")
                                                        .group("123")
                                                        .description("test")
                                                        .code("123")
                                                        .build()
                                        )
                                )
                                .build()
                ))
                .build();
    }

    public static List<ServiceResponse> withDefaultListServiceResponse() {
        return Collections.singletonList(withDefaultListService());
    }

    public static ServiceResponse withDefaultListService() {
        return ServiceResponse.builder()
                .serviceCode("42")
                .serviceName("Entel")
                .build();
    }

    public static List<ServiceResponse> withDefaultListServiceResponseNull() {
        return Collections.singletonList(withDefaultListServiceNull());
    }

    public static ServiceResponse withDefaultListServiceNull() {
        return ServiceResponse.builder()
                .serviceCode(null)
                .serviceName(null)
                .build();
    }
}