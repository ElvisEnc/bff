package bg.com.bo.bff.providers.dtos.response.payment.service;

import java.util.Collections;

public class CategoryMWResponseFixture {
    public static CategoryMWResponse withDefault() {
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
}