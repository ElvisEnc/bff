package bg.com.bo.bff.providers.dtos.response.loyalty;


import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRegisterSubscriptionResponse;

public class LoyaltySEResponseFixture {

    public static LoyaltySystemCodeServerResponse withDefaultSystemCode() {
        return LoyaltySystemCodeServerResponse.builder()
                .codeSystem(1)
                .build();
    }

    public static LoyaltySumPointServerResponse withDefaultSumPoint() {
        return LoyaltySumPointServerResponse.builder()
                .point(1)
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscription() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(201)
                .message("Suscripcion realizada correctamente.")
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscriptionExist() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(400)
                .message("La persona ya se encuentra inscrita a esta campaña.")
                .build();
    }

    public static LoyaltyRegisterSubscriptionResponse withDefaultRegisterSubscriptionException() {
        return LoyaltyRegisterSubscriptionResponse.builder()
                .code(406)
                .message("Error")
                .build();
    }
}
