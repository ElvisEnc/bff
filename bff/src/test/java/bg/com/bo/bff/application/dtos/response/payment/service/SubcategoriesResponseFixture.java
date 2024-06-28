package bg.com.bo.bff.application.dtos.response.payment.service;

import java.util.List;

public class SubcategoriesResponseFixture {

    public static SubcategoriesResponse withDefault() {
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
}
