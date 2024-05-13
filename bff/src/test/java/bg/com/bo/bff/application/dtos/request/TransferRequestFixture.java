package bg.com.bo.bff.application.dtos.request;

import java.math.BigDecimal;

public class TransferRequestFixture {

    public static TransferRequest withDefault() {
        return TransferRequest.builder()
                .targetAccount(TransferRequest.TargetAccount.builder().id("123456789").build())
                .amount(TransferRequest.Amount.builder().amount(BigDecimal.valueOf(4.0)).currency("068").build())
                .data(TransferRequest.Data.builder().description("Test").destinationOfFounds("test destino como minimo 25 caracteres").sourceOfFounds("test origen como minimo 25 caracteres").build())
                .format("PNG")
                .build();
    }

}
