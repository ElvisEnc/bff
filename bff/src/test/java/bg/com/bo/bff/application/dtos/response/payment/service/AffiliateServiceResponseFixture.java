package bg.com.bo.bff.application.dtos.response.payment.service;

import bg.com.bo.bff.providers.dtos.response.ApiDataResponse;

import java.util.Collections;
import java.util.List;

public class AffiliateServiceResponseFixture {
    public static AffiliateServiceResponse withDefault() {
        return AffiliateServiceResponse.builder()
                .affiliateServiceId("123")
                .serviceId("123")
                .serviceName("test")
                .referenceName("test")
                .nameHolder("test")
                .build();
    }

    public static ApiDataResponse<List<AffiliateServiceResponse>> withDataDefault() {
        return ApiDataResponse.of(Collections.singletonList(withDefault()));
    }
}