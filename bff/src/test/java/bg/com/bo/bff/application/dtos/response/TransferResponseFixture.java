package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.response.TransferResponse;

public class TransferResponseFixture {
    public static TransferResponse withDefault() {
        return TransferResponse.builder()
                .idReceipt("24883657")
                .build();
    }
}
