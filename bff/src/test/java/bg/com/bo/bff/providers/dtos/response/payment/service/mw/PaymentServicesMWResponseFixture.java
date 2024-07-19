package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import bg.com.bo.bff.application.dtos.response.payment.service.ValidateAffiliateCriteriaResponse;
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

    public static DebtsConsultationMWResponse withDefaultDebtsResponseMW() {
        return DebtsConsultationMWResponse.builder()
                .data(DebtsConsultationMWResponse.DebtsConsultationMW.builder()
                        .affiliationCode("123")
                        .serviceCode("123")
                        .invoiceTaxId("123")
                        .invoiceName("123")
                        .invoiceCanModifyData("N")
                        .debtDetails(Collections.singletonList(withDefaultDebtsDetailMW()))
                        .build()
                ).build();
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
                                .year(2024)
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
                                                        .searchCriteriaId("123")
                                                        .searchCriteriaIdAbbreviation("123")
                                                        .fields(
                                                                List.of(
                                                                        AffiliateCriteriaMWResponse.DetailData.SearchCriteria.Field.builder()
                                                                                .identifier(123)
                                                                                .label("test")
                                                                                .description("test")
                                                                                .abbreviation("test")
                                                                                .isMandatory("S")
                                                                                .minimumLength("1")
                                                                                .maximumLength("10")
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

    public static ValidateAffiliateCriteriaMWResponse withDefaultValidateAffiliateCriteriaResponse() {
        return ValidateAffiliateCriteriaMWResponse.builder()
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

    public static ListServicesMWResponse withDefaultListServiceMWResponse() {
        return ListServicesMWResponse.builder()
                .data(Collections.singletonList(
                        ListServicesMWResponse.Service.builder()
                                .serviceCode("42")
                                .serviceName("Entel")
                                .build()))
                .build();
    }
}