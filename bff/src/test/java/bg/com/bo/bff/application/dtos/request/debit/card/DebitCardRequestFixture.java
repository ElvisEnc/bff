package bg.com.bo.bff.application.dtos.request.debit.card;

import java.math.BigDecimal;
import java.util.List;

public class DebitCardRequestFixture {
    public static DCLimitsRequest withDefaultDCLimitsRequest() {
        return DCLimitsRequest.builder()
                .dailyAmount(BigDecimal.valueOf(1000))
                .dailyCount("10")
                .period(DCLimitsPeriod.builder()
                        .start("2024-06-06")
                        .end("2024-06-06")
                        .build()
                )
                .build();
    }

    public static ActivateDebitCardRequest withDefaultActivateDebitCardRequest() {
        return ActivateDebitCardRequest.builder()
                .verificationPicture("ds32f1s3d2f13sdf")
                .cardNumber(4444111122223333L)
                .pin(1234)
                .build();
    }

    public static CreateAuthorizationOnlinePurchaseRequest withDefaultCreateAuthOnlinePurchaseRequest(){
        DCLimitsPeriod period = DCLimitsPeriod.builder()
                .start("2024-06-01")
                .end("2024-06-10")
                .build();
        return CreateAuthorizationOnlinePurchaseRequest.builder()
                .amount(new BigDecimal("1000"))
                .period(period)
                .build();
    }

    public static CreateAuthorizationOnlinePurchaseRequest errorCreateAuthOnlinePurchaseRequest(){
        DCLimitsPeriod period = DCLimitsPeriod.builder()
                .start("2024-06-11")
                .end("2024-06-10")
                .build();
        return CreateAuthorizationOnlinePurchaseRequest.builder()
                .amount(new BigDecimal("1000"))
                .period(period)
                .build();
    }

    public static DCAccountsOrderRequest withDefaultDCAccountsOrderRequest() {
        return DCAccountsOrderRequest.builder()
                .data(List.of(DCAccountsOrderRequest.Account.builder()
                        .accountId("1234567890")
                        .build()))
                .build();
    }

    public static DCLockStatusRequest withDefaultDCLockStatusRequest() {
        return DCLockStatusRequest.builder()
                .type(Integer.valueOf("0"))
                .build();
    }

    public static UpdateDebitCardAssuranceRequest withDefaultUpdateDebitCardAssuranceRequest() {
        return UpdateDebitCardAssuranceRequest.builder()
                .email("123@dominio.io")
                .openingRequestFlow(false)
                .openingRequestNumber("123")
                .build();
    }
}
