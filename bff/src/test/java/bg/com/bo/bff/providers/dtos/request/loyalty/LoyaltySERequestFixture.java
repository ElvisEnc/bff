package bg.com.bo.bff.providers.dtos.request.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;

public class LoyaltySERequestFixture {

    public static RegisterSubscriptionRequest withDefaultRegisterSubscription() {
        return RegisterSubscriptionRequest.builder()
                .email("test@test.com")
                .build();
    }

    public static LoyaltyRegisterSubscriptionRequest withDefaultRegisterSubscriptionSE() {
        return LoyaltyRegisterSubscriptionRequest.builder()
                .signatureDigital(true)
                .idPerson("123")
                .codeCampaign("1")
                .jtsOidAccountNumber("1234")
                .email("test@test.com")
                .subscriptionOrigin("GANAMOVIL")
                .build();
    }
}
