package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;

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
}