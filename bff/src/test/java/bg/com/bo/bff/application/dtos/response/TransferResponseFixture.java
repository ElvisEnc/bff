package bg.com.bo.bff.application.dtos.response;

public class TransferResponseFixture {
    public static TransferResponse withDefault() {
        return TransferResponse.builder()
                .data("data")
                .format("format")
                .build();
    }
}
