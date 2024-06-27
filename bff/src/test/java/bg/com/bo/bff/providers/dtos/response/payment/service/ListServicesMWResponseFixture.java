package bg.com.bo.bff.providers.dtos.response.payment.service;

import bg.com.bo.bff.providers.dtos.response.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListServicesMWResponseFixture {
    public static ListServicesMWResponse withDefault() {
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
}