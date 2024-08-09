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

    public static AddQRAccountRequest withDefaultAddQRAccountRequest() {
        return AddQRAccountRequest.builder()
                .accountNumber("123456789")
                .holderName("Prueba holder")
                .identificationNumber("123456789")
                .bankCode("1234")
                .reference("Referencia")
                .build();
    }

    public static AddAchAccountRequest withDefaultAddAchAccountRequest() {
        return AddAchAccountRequest.builder()
                .isFavorite("N")
                .isEnabled("S")
                .reference("referencia con espacios")
                .destinationAccountNumber("123456789")
                .destinationBankCode("123456789")
                .destinationBranchOfficeCode("123456789")
                .destinationAccountTypeCode("123456789")
                .destinationHolderName("123456789")
                .destinationIDNumber("123456789")
                .email("reynaldo@gmai.com")
                .build();
    }

    public static AddThirdAccountRequest withDefaultAddThirdAccountRequest() {
        return AddThirdAccountRequest.builder()
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();
    }

    public static AddWalletAccountRequest withDefaultAddWalletAccountRequest() {
        return AddWalletAccountRequest.builder()
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();
    }
}