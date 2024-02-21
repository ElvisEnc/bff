package bg.com.bo.bff.controllers.models;

import bg.com.bo.bff.controllers.response.TransferResponse;

public class TransferResponseFixture {
    public static TransferResponse withDefault() {
        return TransferResponse.builder()
                .idReceipt("24883657")
                .build();
    }
}
