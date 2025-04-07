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
                .firmaDigital(true)
                .idPersona("123")
                .codigoCampana("1")
                .numeroCuentaJTS_OID("1234")
                .email("test@test.com")
                .suscripcionOrigen("GANAMOVIL")
                .build();
    }
}
