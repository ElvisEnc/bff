package bg.com.bo.bff.application.dtos.request.loyalty;


public class LoyaltyRequestFixture {

    public static RegisterSubscriptionRequest withDefaultRegisterSubscription() {
        return RegisterSubscriptionRequest.builder()
                .email("test@test.com")
                .build();
    }
}
