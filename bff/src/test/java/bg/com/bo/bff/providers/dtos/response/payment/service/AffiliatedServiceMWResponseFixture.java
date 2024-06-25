package bg.com.bo.bff.providers.dtos.response.payment.service;

import bg.com.bo.bff.providers.dtos.response.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AffiliatedServiceMWResponseFixture {
    public static AffiliatedServiceMWResponse withDefault() {
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