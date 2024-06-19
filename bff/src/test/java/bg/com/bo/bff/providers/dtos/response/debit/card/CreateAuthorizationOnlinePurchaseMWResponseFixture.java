package bg.com.bo.bff.providers.dtos.response.debit.card;


public class CreateAuthorizationOnlinePurchaseMWResponseFixture {
    public static CreateAuthorizationOnlinePurchaseMWResponse withDefault() {
        return new CreateAuthorizationOnlinePurchaseMWResponse(
                "12334",
                "OK",
                "0"
        );
    }

    public static CreateAuthorizationOnlinePurchaseMWResponse errorMW() {
        return new CreateAuthorizationOnlinePurchaseMWResponse(
                null,
                "Error",
                "0"
        );
    }
}
