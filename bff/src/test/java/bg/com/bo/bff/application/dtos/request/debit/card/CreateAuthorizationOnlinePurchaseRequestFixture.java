package bg.com.bo.bff.application.dtos.request.debit.card;

import java.math.BigDecimal;

public class CreateAuthorizationOnlinePurchaseRequestFixture {
    public static CreateAuthorizationOnlinePurchaseRequest withDefault(){
        DCLimitsPeriod period = DCLimitsPeriod.builder()
                .start("2024-06-01")
                .end("2024-06-10")
                .build();
        return CreateAuthorizationOnlinePurchaseRequest.builder()
                .amount(new BigDecimal("1000"))
                .period(period)
                .build();
    }

    public static CreateAuthorizationOnlinePurchaseRequest errorCreate(){
        DCLimitsPeriod period = DCLimitsPeriod.builder()
                .start("2024-06-11")
                .end("2024-06-10")
                .build();
        return CreateAuthorizationOnlinePurchaseRequest.builder()
                .amount(new BigDecimal("1000"))
                .period(period)
                .build();
    }
}
