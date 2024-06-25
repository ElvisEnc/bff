package bg.com.bo.bff.application.dtos.response.payment.service;

import bg.com.bo.bff.providers.dtos.response.ApiDataResponse;
import java.util.Collections;
import java.util.List;

public class CategoryResponseFixture {
    public static CategoryResponse withDefault() {
        return CategoryResponse.builder()
                .categoryId("1")
                .categoryName("category")
                .build();
    }

    public static ApiDataResponse<List<CategoryResponse>> withDefaultData() {
        return ApiDataResponse.of(Collections.singletonList(withDefault()));
    }
}