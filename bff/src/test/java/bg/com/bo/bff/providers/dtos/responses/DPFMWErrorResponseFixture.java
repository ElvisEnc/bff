package bg.com.bo.bff.providers.dtos.responses;

import bg.com.bo.bff.providers.models.enums.middleware.DPFMiddlewareError;

import java.util.ArrayList;
import java.util.List;

public class DPFMWErrorResponseFixture {
    public static ApiErrorResponse withDefault() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(DPFMiddlewareError.MDWDPF_002.getCodeMiddleware())
                .description("Sarasa")
                .build();

        list.add(errorDetailResponse);

        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }
}