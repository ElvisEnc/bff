package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.request.TransferRequest;

public class TransferRequestFixture {

    public static TransferRequest withDefault() {
        return TransferRequest.builder()
                .fromAccountId("1234567")
                .toAccountId("12345689")
                .currency("068")
                .amount("2.2")
                .description("esto es una transfer")
                .sourceOfFunds("estado detalle")
                .destinationOfFunds("estado detalle fin")
                .build();

    }
}
