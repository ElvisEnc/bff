package bg.com.bo.bff.application.dtos.response.payment.services;

import bg.com.bo.bff.application.dtos.response.SubcategoriesResponse;

import java.util.List;

public class SubcategoriesResponseFixture {

    public static SubcategoriesResponse withDefault(){
        return new SubcategoriesResponse(
                List.of(
                        SubcategoriesResponse.Subcategory.builder()
                                .id(1)
                                .categoryId(2)
                                .description("UNIVERSIDADES")
                                .build(),
                        SubcategoriesResponse.Subcategory.builder()
                                .id(1)
                                .categoryId(2)
                                .description("COLEGIOS")
                                .build(),
                        SubcategoriesResponse.Subcategory.builder()
                                .id(1)
                                .categoryId(2)
                                .description("INSTITUTOS")
                                .build()
                )
        );
    }
}
