package bg.com.bo.bff.application.dtos.request.destination.account;


public class DestinationAccountRequestFixture {
    public static DestinationAccountRequest withDefault() {
        return DestinationAccountRequest.builder()
                .name("")
                .pagination(
                        PaginationRequest.builder()
                                .page(1)
                                .pageSize(10)
                                .build()
                )
                .build();
    }

    public static AddQRAccountRequest withDefaultAddQRRequest() {
        return AddQRAccountRequest.builder()
                .accountNumber("123456789")
                .holderName("Prueba holder")
                .identificationNumber("123456789")
                .bankCode("1234")
                .reference("Referencia")
                .build();
    }
}