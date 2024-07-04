package bg.com.bo.bff.application.dtos.response.payment.service;

import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;

import java.util.Collections;
import java.util.List;

public class PaymentServiceResponseFixture {
    public static AffiliateServiceResponse withDefaultAffiliateServiceResponse() {
        return AffiliateServiceResponse.builder()
                .affiliateServiceId("123")
                .serviceId("123")
                .serviceName("test")
                .referenceName("test")
                .nameHolder("test")
                .build();
    }

    public static ApiDataResponse<List<AffiliateServiceResponse>> withDataDefaultListAffiliateServiceResponse() {
        return ApiDataResponse.of(Collections.singletonList(withDefaultAffiliateServiceResponse()));
    }

    public static CategoryResponse withDefaultCategoryResponse() {
        return CategoryResponse.builder()
                .categoryId("1")
                .categoryName("category")
                .build();
    }

    public static ApiDataResponse<List<CategoryResponse>> withDefaultDataListCategoryResponse() {
        return ApiDataResponse.of(Collections.singletonList(withDefaultCategoryResponse()));
    }

    public static ListServicesResponse withDefaultListServicesResponse() {
        return new ListServicesResponse(
                List.of(
                        ListServicesResponse.Service.builder()
                                .serviceId("1")
                                .description("Servicio 1")
                                .build(),
                        ListServicesResponse.Service.builder()
                                .serviceId("2")
                                .description("Servicio 2")
                                .build(),
                        ListServicesResponse.Service.builder()
                                .serviceId("3")
                                .description("Servicio 3")
                                .build()
                )
        );
    }

    public static SubcategoriesResponse withDefaultSubcategoriesResponse() {
        return new SubcategoriesResponse(
                List.of(
                        SubcategoriesResponse.Subcategory.builder()
                                .id(1)
                                .categoryId(2)
                                .description("UNIVERSIDADES")
                                .hasCity(Boolean.FALSE)
                                .build(),
                        SubcategoriesResponse.Subcategory.builder()
                                .id(1)
                                .categoryId(2)
                                .description("COLEGIOS")
                                .hasCity(Boolean.TRUE)
                                .build(),
                        SubcategoriesResponse.Subcategory.builder()
                                .id(1)
                                .categoryId(2)
                                .description("INSTITUTOS")
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
                                .searchCriteriaId("123")
                                .searchCriteriaIdAbbreviation("123")
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
}