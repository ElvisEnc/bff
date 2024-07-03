package bg.com.bo.bff.providers.dtos.response.generic;

import bg.com.bo.bff.providers.dtos.response.generic.ErrorMiddlewareProvider;

import java.util.Collections;

public class ErrorMiddlewareProviderFixture {
    public static ErrorMiddlewareProvider errorBadRequest() {
        return ErrorMiddlewareProvider.builder()
                .errorDetailResponse(Collections.singletonList(ErrorMiddlewareProvider.ErrorDetailProvider.builder()
                        .code("BAD_REQUEST")
                        .description("BAD_REQUEST")
                        .build()))
                .build();
    }
}