package bg.com.bo.bff.providers.dtos.request.debit.card;

import java.math.BigDecimal;

public class CreateAuthorizationOnlinePurchaseMWRequestFixture {
    public static CreateAuthorizationOnlinePurchaseMWRequest withDefault(){
        return CreateAuthorizationOnlinePurchaseMWRequest.builder()
                .idPci("12121")
                .action("A")
                .initialDate("2024-06-01")
                .finalDate("2024-06-10")
                .amount(new BigDecimal("1000"))
                .intInitial(1)
                .intFinal(10)
                .build();
    }
}
