package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.request.transfer.AmountTransfer;
import bg.com.bo.bff.application.dtos.request.transfer.DataTransfer;
import bg.com.bo.bff.application.dtos.request.transfer.TargetAccount;
import bg.com.bo.bff.application.dtos.request.transfer.TransferRequest;

import java.math.BigDecimal;

public class TransferRequestFixture {

    public static TransferRequest withDefault() {
        return TransferRequest.builder()
                .targetAccount(TargetAccount.builder().id(123456789).build())
                .amount(AmountTransfer.builder().amount(BigDecimal.valueOf(4.0)).currency("068").build())
                .data(DataTransfer.builder().description("Test").destinationOfFounds("test destino como minimo 25 caracteres").sourceOfFounds("test origen como minimo 25 caracteres").build())
                .format("PNG")
                .build();
    }
}
