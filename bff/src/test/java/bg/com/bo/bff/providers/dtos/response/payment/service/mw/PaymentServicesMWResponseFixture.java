package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import bg.com.bo.bff.providers.dtos.request.payment.services.mw.DebtsConsultationMWRequest;
import bg.com.bo.bff.providers.dtos.request.payment.services.mw.PaymentDebtsMWRequest;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DataRegisterServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DataServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.DependencyServiceAffiliationMW;
import bg.com.bo.bff.providers.dtos.request.personal.information.affiliation.ServiceAffiliationMWRequest;
import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentServicesMWResponseFixture {
    public static ListServicesMWResponse withDefaultListServicesMWResponse() {
        return ListServicesMWResponse.builder()
                .data(Collections.singletonList(withDefaultItemServiceMW()))
                .build();
    }

    public static ListServicesMWResponse.Service withDefaultItemServiceMW() {
        return ListServicesMWResponse.Service.builder()
                .serviceCode("123")
                .serviceName("test")
                .build();
    }

    public static ApiErrorResponse withErrorMDWPSM005() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(PaymentServicesMiddlewareError.MDWPSM_005.getCodeMiddleware())
                .description(PaymentServicesMiddlewareError.MDWPSM_005.getMessage())
                .build();
        list.add(errorDetailResponse);
        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }

    public static ApiErrorResponse withErrorMDWPSM007() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(PaymentServicesMiddlewareError.MDWPSM_007.getCodeMiddleware())
                .description(PaymentServicesMiddlewareError.MDWPSM_007.getMessage())
                .build();
        list.add(errorDetailResponse);
        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }


    public static SubcategoriesMWResponse withDefaultSubcategoriesMWResponse() {
        return new SubcategoriesMWResponse(
                List.of(
                        SubcategoriesMWResponse.SubcategoryMW.builder()
                                .subCategoryCod(5)
                                .categoryCod(2)
                                .description("UNIVERSIDADES")
                                .hasCity("N")
                                .build(),
                        SubcategoriesMWResponse.SubcategoryMW.builder()
                                .subCategoryCod(4)
                                .categoryCod(2)
                                .description("COLEGIOS")
                                .hasCity("S")
                                .build(),
                        SubcategoriesMWResponse.SubcategoryMW.builder()
                                .subCategoryCod(6)
                                .categoryCod(2)
                                .description("INSTITUTOS")
                                .hasCity("N")
                                .build()
                )
        );
    }

    public static String errorMDWPSM_003() {
        return "{\"code\":406,\"errorType\":\"Technical\",\"errorDetailResponse\":[{\"code\":\"MDWPSM-003\",\"description\":\"Not found sub categories\"}]}";
    }

    public static SubCategoryCitiesMWResponse withDefaultSubCategoryCitiesMWResponse() {
        List<SubCategoryCitiesMWResponse.CityMW> data = List.of(
                new SubCategoryCitiesMWResponse.CityMW(3, "COCHABAMBA"),
                new SubCategoryCitiesMWResponse.CityMW(7, "SANTA CRUZ")

        );
        return new SubCategoryCitiesMWResponse(data);
    }

    public static String errorMDWPSM004() {
        return "{\"code\":406,\"errorType\":\"Technical\",\"errorDetailResponse\":[{\"code\":\"MDWPSM-004\",\"description\":\"Not found cities\"}]}";
    }

    public static DeleteAffiliateServiceMWResponse withDefaultDeleteAffiliateServiceMWResponse() {
        return new DeleteAffiliateServiceMWResponse(
                new DeleteAffiliateServiceMWResponse.Response("1234")
        );
    }

    public static CategoryMWResponse withDefaultCategoryMWResponse() {
        return CategoryMWResponse.builder()
                .data(Collections.singletonList(withDefaultCategoryMW()))
                .build();
    }

    public static CategoryMWResponse.CategoryMW withDefaultCategoryMW() {
        return CategoryMWResponse.CategoryMW.builder()
                .idType("1")
                .description("category")
                .idCategory("1")
                .detail("1")
                .build();
    }

    public static DebtsConsultationMWRequest withDefaultDebtsRequestMW() {
        return new DebtsConsultationMWRequest(123, 123, 2024, 123);
    }

    public static DebtsConsultationMWResponse withDefaultDebtsResponseMW() {
        return DebtsConsultationMWResponse.builder()
                .affiliationCode("123")
                .serviceCode("123")
                .invoiceTaxId("123")
                .invoiceName("123")
                .invoiceCanModifyData("123")
                .debtDetails(Collections.singletonList(withDefaultDebtsDetailMW()))
                .build();
    }

    public static DebtsConsultationMWResponse.DebtsConsultationDetail withDefaultDebtsDetailMW() {
        return DebtsConsultationMWResponse.DebtsConsultationDetail.builder()
                .description("123")
                .referenceCode("123")
                .monthPeriod(123)
                .year(123)
                .commissionAmount(BigDecimal.valueOf(10.00))
                .currencyCode("123")
                .amount(BigDecimal.valueOf(10.00))
                .accumulatedAmount(BigDecimal.valueOf(10.00))
                .identifier(123L)
                .validationType("123")
                .detail("123")
                .additionalDataDetails("123")
                .paymentPlanCode("123")
                .idGeneratedForDebt("123")
                .penaltyPayment(BigDecimal.valueOf(10.00))
                .concept("123")
                .build();
    }

    public static PaymentDebtsMWRequest withDefaultPaymentDebtsMWRequest() {
        return PaymentDebtsMWRequest.builder()
                .ownerAccount(PaymentDebtsMWRequest.PaymentOwnerAccount.builder()
                        .schemeName("scheme123")
                        .personId("person123")
                        .companyId("company123")
                        .build())
                .instructedAmount(PaymentDebtsMWRequest.PaymentAmount.builder()
                        .currency("USD")
                        .amount(BigDecimal.valueOf(100.00))
                        .build())
                .debtorAccount(PaymentDebtsMWRequest.PaymentDebtor.builder()
                        .schemeName("scheme123")
                        .identification("ident123")
                        .build())
                .supplementaryData(PaymentDebtsMWRequest.PaymentSupplementary.builder()
                        .idGeneratedForDebt("324a029a-553f-4acb-abf4-4dcb25574463")
                        .invoiceNITCI("12546878")
                        .invoiceName("Juan Perez")
                        .company("Test Company")
                        .affiliationCode("aff123")
                        .serviceCode("svc123")
                        .description("Test Payment")
                        .build())
                .risk(PaymentDebtsMWRequest.PaymentRisk.builder()
                        .paymentContextCode("context123")
                        .build())
                .build();
    }

    public static PaymentDebtsMWResponse withDefaultPaymentDebtsMWResponse() {
        return new PaymentDebtsMWResponse(
                "success",
                "mae123",
                "txn123",
                new PaymentDebtsMWResponse.ReceiptDetail(
                        "2024-07-11",
                        "10:00",
                        "entry123",
                        "123456789",
                        "John Doe",
                        BigDecimal.valueOf(100.00),
                        "USD",
                        BigDecimal.valueOf(100.00),
                        "USD",
                        "1.0",
                        "Test Company",
                        "aff123",
                        "Test Payment",
                        "svc123",
                        "voucher123"
                )
        );
    }

    public static ServiceAffiliationMWRequest withDefaultServiceAffiliationMWRequest() {
        return ServiceAffiliationMWRequest.builder()
                .serviceCode("42")
                .criteriaSearchId("24")
                .referenceName("referencia")
                .year("2024")
                .personId("123")
                .accountNumber("0")
                .isTemporal("N")
                .searchFields(List.of(withDefaultDependencyServiceAffiliationMW()))
                .dataAffiliation(List.of(withDefaultDataServiceAffiliationMW()))
                .build();
    }

    public static DependencyServiceAffiliationMW withDefaultDependencyServiceAffiliationMW() {
        return new DependencyServiceAffiliationMW(
                "28",
                "73166120"
        );
    }

    public static DataServiceAffiliationMW withDefaultDataServiceAffiliationMW() {
        return new DataServiceAffiliationMW(
                "1",
                "S/N",
                "73166120",
                "desc",
                null,
                List.of(withDefaultDataRegisterServiceAffiliationMW())
        );
    }

    public static DataRegisterServiceAffiliationMW withDefaultDataRegisterServiceAffiliationMW() {
        return new DataRegisterServiceAffiliationMW(
                "cuenta",
                "73166120",
                null,
                null,
                null,
                null,
                null
        );
    }

    public static ServiceAffiliationMWResponse withDefaultServiceAffiliationMWResponse() {
        return new ServiceAffiliationMWResponse("1946919");
    }

    public static AffiliatedServiceMWResponse withDefaultAffiliatedServiceMWResponse() {
        return AffiliatedServiceMWResponse.builder()
                .data(Collections.singletonList(withDefaultAffiliatedServiceMW()))
                .build();
    }

    public static AffiliatedServiceMWResponse.AffiliatedServiceMW withDefaultAffiliatedServiceMW() {
        return AffiliatedServiceMWResponse.AffiliatedServiceMW.builder()
                .serviceCode("123")
                .serviceDesc("test")
                .referenceName("test")
                .nameHolder("test")
                .affiliationCode("123")
                .internalCod("123")
                .year("123")
                .descriptionTag("test")
                .stateContingency("test")
                .msjContingency("test")
                .build();
    }

    public static AffiliateCriteriaMWResponse withDefaultAffiliateCriteriaMWResponse() {
        return AffiliateCriteriaMWResponse.builder()
                .data(
                        AffiliateCriteriaMWResponse.DetailData.builder()
                                .serviceCode("123")
                                .year(123)
                                .subServices(List.of(
                                        AffiliateCriteriaMWResponse.DetailData.SubService.builder()
                                                .labelCriteria("test")
                                                .abbreviation("test")
                                                .build()
                                ))
                                .searchCriteria(
                                        List.of(
                                                AffiliateCriteriaMWResponse.DetailData.SearchCriteria.builder()
                                                        .labelCriteria("test")
                                                        .description("test")
                                                        .searchCriteriaId("test")
                                                        .searchCriteriaIdAbbreviation("test")
                                                        .fields(
                                                                List.of(
                                                                        AffiliateCriteriaMWResponse.DetailData.SearchCriteria.Field.builder()
                                                                                .identifier(1)
                                                                                .label("test")
                                                                                .description("test")
                                                                                .abbreviation("test")
                                                                                .isMandatory("test")
                                                                                .minimumLength("1")
                                                                                .maximumLength("1")
                                                                                .dataTypeCode("1")
                                                                                .values(
                                                                                        List.of(
                                                                                                AffiliateCriteriaMWResponse.DetailData.SearchCriteria.Field.Value.builder()
                                                                                                        .code("test")
                                                                                                        .description("test")
                                                                                                        .build()
                                                                                        )
                                                                                )
                                                                                .build()
                                                                )
                                                        )
                                                        .build()
                                        )
                                )
                                .build()
                )
                .build();
    }
}