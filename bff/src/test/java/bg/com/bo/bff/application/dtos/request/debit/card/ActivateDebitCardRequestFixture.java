package bg.com.bo.bff.application.dtos.request.debit.card;

public class ActivateDebitCardRequestFixture {
    public static ActivateDebitCardRequest withDefault() {
        return ActivateDebitCardRequest.builder()
                .verificationPicture("ds32f1s3d2f13sdf")
                .cardNumber(4444111122223333L)
                .pin(1234)
                .build();
    }
}